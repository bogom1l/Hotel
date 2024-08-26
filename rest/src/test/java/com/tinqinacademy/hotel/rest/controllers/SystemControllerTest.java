package com.tinqinacademy.hotel.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tinqinacademy.hotel.api.operations.system.registerguest.GuestInput;
import com.tinqinacademy.hotel.api.operations.system.registerguest.RegisterGuestInput;
import com.tinqinacademy.hotel.api.operations.system.registerguest.RegisterGuestOutput;
import com.tinqinacademy.hotel.persistence.model.Booking;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.hotel.api.operations.hotel.getroombasicinfo.GetRoomBasicInfoOutput;
import com.tinqinacademy.hotel.api.restroutes.RestApiRoutes;
import com.tinqinacademy.hotel.persistence.configuration.DataSeeder;
import com.tinqinacademy.hotel.persistence.repository.BedRepository;
import com.tinqinacademy.hotel.persistence.repository.BookingRepository;
import com.tinqinacademy.hotel.persistence.repository.GuestRepository;
import com.tinqinacademy.hotel.persistence.repository.RoomRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY, connection = EmbeddedDatabaseConnection.H2)
class SystemControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private BedRepository bedRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private DataSeeder dataSeeder;

    @BeforeEach
    public void setup() throws Exception {

        dataSeeder.run(new ApplicationArguments() {

            @Override
            public String[] getSourceArgs() {
                return new String[0];
            }

            @Override
            public Set<String> getOptionNames() {
                return Set.of();
            }

            @Override
            public boolean containsOption(String name) {
                return false;
            }

            @Override
            public List<String> getOptionValues(String name) {
                return List.of();
            }

            @Override
            public List<String> getNonOptionArgs() {
                return List.of();
            }
        });
    }

    @AfterEach
    public void afterEach() {
        bookingRepository.deleteAll();
        roomRepository.deleteAll();
        bedRepository.deleteAll();
        guestRepository.deleteAll();
    }

    @Test
    void registerGuestReturnsNotFound() throws Exception {
        String roomId = roomRepository.findAll().getFirst().getId().toString();

        List<GuestInput> guests = List.of(
                GuestInput.builder()
                        .startDate(LocalDate.of(2024, 10, 11))
                        .endDate(LocalDate.of(2024, 10, 15))
                        .firstName("John")
                        .lastName("Doe")
                        .phoneNumber("123456789")
                        .idCardNumber("123456789")
                        .idCardValidity(LocalDate.of(2024, 10, 15))
                        .idCardIssueAuthority("Authority")
                        .idCardIssueDate(LocalDate.of(2024, 10, 11))
                        .birthdate(LocalDate.of(1990, 10, 11))
                        .build()
        );

        RegisterGuestInput input = RegisterGuestInput.builder()
                .roomId(roomId)
                .guests(guests)
                .build();

        mvc.perform(post(RestApiRoutes.REGISTER_GUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().isNotFound());
    }


    @Test
    void registerGuestReturnsCreated() throws Exception {
        String roomId = roomRepository.findAll().getFirst().getId().toString();
        Booking booking = bookingRepository.findAll().getFirst();

        List<GuestInput> guests = List.of(
                GuestInput.builder()
                        .startDate(booking.getStartDate())
                        .endDate(booking.getEndDate())
                        .firstName("John")
                        .lastName("Doe")
                        .phoneNumber("123456789")
                        .idCardNumber("123456789")
                        .idCardValidity(LocalDate.of(2024, 10, 15))
                        .idCardIssueAuthority("Authority")
                        .idCardIssueDate(LocalDate.of(2024, 10, 11))
                        .birthdate(LocalDate.of(1990, 10, 11))
                        .build()
        );

        RegisterGuestInput input = RegisterGuestInput.builder()
                .roomId(roomId)
                .guests(guests)
                .build();

        mvc.perform(post(RestApiRoutes.REGISTER_GUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().isCreated());
    }

    @Test
    void getReportReturnsNotFound() throws Exception {
        mvc.perform(get(RestApiRoutes.GET_REPORT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getReportReturnsOk() throws Exception {
        String roomId = roomRepository.findAll().getFirst().getId().toString();

        mvc.perform(get(RestApiRoutes.GET_REPORT)
                        .param("roomId", roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk());
    }


}