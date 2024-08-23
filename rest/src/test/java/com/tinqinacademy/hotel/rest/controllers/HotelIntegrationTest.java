//package com.tinqinacademy.hotel.rest.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.tinqinacademy.hotel.api.restroutes.RestApiRoutes;
//import com.tinqinacademy.hotel.persistence.model.Bed;
//import com.tinqinacademy.hotel.persistence.model.Room;
//import com.tinqinacademy.hotel.persistence.model.enums.BathroomType;
//import com.tinqinacademy.hotel.persistence.model.enums.BedSize;
//import com.tinqinacademy.hotel.persistence.repository.BedRepository;
//import com.tinqinacademy.hotel.persistence.repository.BookingRepository;
//import com.tinqinacademy.hotel.persistence.repository.RoomRepository;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.math.BigDecimal;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.List;
//import java.util.UUID;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY, connection = EmbeddedDatabaseConnection.H2)
//public class HotelIntegrationTest {
//    @Autowired
//    private MockMvc mvc;
//
//    @Autowired
//    private ObjectMapper mapper;
//
//    @Autowired
//    private BedRepository bedRepository;
//
//    @Autowired
//    private RoomRepository roomRepository;
//
//    @Autowired
//    private BookingRepository bookingRepository;
//
//    @BeforeEach
//    public void setup() {
//        //todo
//    }
//
//    @AfterEach
//    public void afterEach() {
//        bookingRepository.deleteAll();
//        roomRepository.deleteAll();
//        bedRepository.deleteAll();
//    }
//
//
//
//}
