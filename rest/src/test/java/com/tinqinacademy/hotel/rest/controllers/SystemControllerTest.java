package com.tinqinacademy.hotel.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.api.operations.system.registerguest.GuestInput;
import com.tinqinacademy.hotel.api.operations.system.registerguest.RegisterGuestInput;
import com.tinqinacademy.hotel.api.operations.system.registerguest.RegisterGuestOutput;
import com.tinqinacademy.hotel.api.operations.system.updatepartiallyroom.UpdatePartiallyRoomInput;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.persistence.model.Booking;
import com.tinqinacademy.hotel.persistence.model.Room;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        Room room = roomRepository.findAll().getFirst();

        mvc.perform(get(RestApiRoutes.GET_REPORT)
                        .param("roomNumber", room.getRoomNumber())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk());
    }

    @Test
    void createRoomReturnsCreated() throws Exception {
        CreateRoomInput input = CreateRoomInput.builder()
                .roomNumber("H864")
                .bedSize("double")
                .bathroomType("private")
                .floor(1)
                .price(BigDecimal.valueOf(100))
                .build();

        mvc.perform(post(RestApiRoutes.CREATE_ROOM)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input))
                        .characterEncoding("UTF-8"))
                .andExpect(status().isCreated());
    }

    @Test
    void updateRoomReturnsOk() throws Exception {
        Room room = roomRepository.findAll().getFirst();
        UpdateRoomInput input = UpdateRoomInput.builder()
                .roomNumber("H864")
                .bedSize("double")
                .bathroomType("private")
                .price(BigDecimal.valueOf(100))
                .build();

        mvc.perform(put(RestApiRoutes.UPDATE_ROOM, room.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input))
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk());
    }

    @Test
    void updatePartiallyRoomReturnsOk() throws Exception {
        Room room = roomRepository.findAll().getFirst();
        UpdatePartiallyRoomInput input = UpdatePartiallyRoomInput.builder()
                .roomNumber("H864")
                .bedSize("double")
                .bathroomType("private")
                .price(BigDecimal.valueOf(100))
                .build();

        mvc.perform(patch(RestApiRoutes.UPDATE_PARTIALLY_ROOM, room.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input))
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk());
    }

    @Test
    void updatePartiallyRoomReturnsNotFound() throws Exception {
        UUID roomId = UUID.randomUUID();
        UpdatePartiallyRoomInput input = UpdatePartiallyRoomInput.builder()
                .roomNumber("H864")
                .bedSize("double")
                .bathroomType("private")
                .price(BigDecimal.valueOf(100))
                .build();

        mvc.perform(patch(RestApiRoutes.UPDATE_PARTIALLY_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input))
                        .characterEncoding("UTF-8"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteRoomReturnsOk() throws Exception {
        Room room = roomRepository.findAll().getFirst();

        mvc.perform(delete(RestApiRoutes.DELETE_ROOM, room.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteRoomReturnsNotFound() throws Exception {
        UUID roomId = UUID.randomUUID();

        mvc.perform(delete(RestApiRoutes.DELETE_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isNotFound());
    }
    

}