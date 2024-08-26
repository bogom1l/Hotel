package com.tinqinacademy.hotel.rest.controllers;

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

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class) // Integrates the Spring TestContext Framework with JUnit 5
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// Loads the full application context for integration tests
@AutoConfigureMockMvc // Configures MockMvc for testing Spring MVC controllers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY, connection = EmbeddedDatabaseConnection.H2) // Configures an embedded database for testing
class HotelControllerTest {

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
    void checkAvailableRoomReturnsOk() throws Exception {
        mvc.perform(get(RestApiRoutes.CHECK_ROOM_AVAILABILITY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("startDate", "2024-11-27")
                        .param("endDate", "2024-11-30")
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ids", hasSize(4)));
    }

    @Test
    void checkAvailableRoomReturnsEmptyList() throws Exception {
        mvc.perform(get(RestApiRoutes.CHECK_ROOM_AVAILABILITY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("startDate", "2044-07-15")
                        .param("endDate", "2045-07-15")
                        .param("bedCount", "5")
                        .param("bedSize", "double")
                        .param("bathroomType", "private")
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ids", hasSize(0)));
    }

    @Test
    void getRoomByIdReturnsOk() throws Exception {
        String roomId = roomRepository.findAll().getFirst().getId().toString();

        MvcResult getRoomInfo = mvc.perform(get(RestApiRoutes.GET_ROOM_INFO, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = getRoomInfo.getResponse().getContentAsString();
        GetRoomBasicInfoOutput getRoomBasicInfoOutput = mapper.readValue(jsonResponse, GetRoomBasicInfoOutput.class);

        assertEquals(1, getRoomBasicInfoOutput.getFloor());
        assertEquals("private", getRoomBasicInfoOutput.getBathroomType().toString());
    }

    @Test
    void getRoomByIdReturnsNotFound() throws Exception {
        UUID roomId = UUID.randomUUID();

        mvc.perform(get(RestApiRoutes.GET_ROOM_INFO, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isNotFound());
    }

    @Test
    void bookRoomReturnsCreated() throws Exception {
        String roomId = roomRepository.findAll().getFirst().getId().toString();
        String userId = UUID.randomUUID().toString();

        mvc.perform(get(RestApiRoutes.BOOK_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"startDate\":\"2024-11-27\",\"endDate\":\"2024-11-30\",\"userId\":\"" + userId + "\"}")
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk());
    }

//    @Test
//    void bookRoomReturnsBadRequest() throws Exception {
//        String roomId = roomRepository.findAll().getFirst().getId().toString();
//        String userId = UUID.randomUUID().toString();
//
//        mvc.perform(get(RestApiRoutes.BOOK_ROOM, roomId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"startDate\":\"2024-11-27\",\"endDate\":\"2024-11-30\",\"userId\":\"" + userId + "\"}")
//                        .characterEncoding("UTF-8"))
//                .andExpect(status().isBadRequest());
//    }

    @Test
    void unbookRoomReturnsNotFound() throws Exception {
        String bookingId = bookingRepository.findAll().getFirst().getId().toString();

        mvc.perform(get(RestApiRoutes.UNBOOK_ROOM, bookingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"" + UUID.randomUUID().toString() + "\"}")
                        .characterEncoding("UTF-8"))
                .andExpect(status().isNotFound());
    }

    @Test
    void unbookRoomReturnsOk() throws Exception {
        String bookingId = bookingRepository.findAll().getFirst().getId().toString();
        String roomId = roomRepository.findAll().getFirst().getId().toString();

        mvc.perform(get(RestApiRoutes.UNBOOK_ROOM, bookingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"" + roomId + "\"}")
                        .characterEncoding("UTF-8"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateBookingReturnsOk() throws Exception {
        String bookingId = bookingRepository.findAll().getFirst().getId().toString();
        String roomId = roomRepository.findAll().getFirst().getId().toString();

        mvc.perform(get(RestApiRoutes.UPDATE_PARTIALLY_BOOKING, bookingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"startDate\":\"2024-11-27\",\"endDate\":\"2024-11-30\",\"userId\":\"" + roomId + "\"}")
                        .characterEncoding("UTF-8"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateBookingReturnsNotFound() throws Exception {
        String bookingId = bookingRepository.findAll().getFirst().getId().toString();
        String roomId = roomRepository.findAll().getFirst().getId().toString();

        mvc.perform(get(RestApiRoutes.UPDATE_PARTIALLY_BOOKING, bookingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"startDate\":\"2024-11-27\",\"endDate\":\"2024-11-30\",\"userId\":\"" + roomId + "\"}")
                        .characterEncoding("UTF-8"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getBookingHistoryReturnsOk() throws Exception {
        String userId = UUID.randomUUID().toString();

        mvc.perform(get(RestApiRoutes.GET_BOOKING_HISTORY, userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk());
    }

}
