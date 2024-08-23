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

//        Bed bed = Bed.builder()
//                .id(UUID.fromString("a1111b03-e491-4ff3-a617-2b2b3a190a59"))
//                .bedSize(BedSize.DOUBLE)
//                .capacity(BedSize.DOUBLE.getCapacity())
//                .build();
//
//        bedRepository.save(bed); //√
//
//        Room room = Room.builder()
//                .id(UUID.fromString("4446112d-6611-4562-a2db-ee1e4f4c3894"))
//                .roomNumber("A912")
//                .price(BigDecimal.valueOf(100))
//                .beds((List<Bed>) new HashSet<>(Collections.singletonList(bed)))
//                .floor(5)
//                .bathroomType(BathroomType.PRIVATE)
//                .build();
//
//        roomRepository.save(room);
//
//        Guest guest = Guest.builder()
//                .id(UUID.fromString("88888b03-e491-4ff3-a617-2b2b3a190a59"))
//                .firstName("John")
//                .lastName("Doe")
//                .birthdate(LocalDate.parse("2001-01-01"))
//                .phoneNumber("08912355122")
//                .idCardValidity(LocalDate.parse("2028-01-01"))
//                .idCardIssueDate(LocalDate.parse("2015-01-01"))
//                .idCardIssueAuthority("Sofia")
//                .idCardNumber("123456789")
//                .build();
//
//        guestRepository.save(guest);
//
//        Booking booking = Booking.builder()
//                .id(UUID.fromString("cccc1b03-e491-4ff3-a617-2b2b3a190a59"))
//                .room(room)
//                .userId(UUID.fromString("bbbb1b03-e491-4ff3-a617-2b2b3a190a59"))
//                .startDate(LocalDate.parse("2021-01-01"))
//                .endDate(LocalDate.parse("2021-01-10"))
//                .totalPrice(room.getPrice())
//                .guests(Set.of(guest))
//                .build();
//
//        bookingRepository.save(booking);

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


}
