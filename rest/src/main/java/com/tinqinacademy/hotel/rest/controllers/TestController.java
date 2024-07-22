package com.tinqinacademy.hotel.rest.controllers;

import com.tinqinacademy.hotel.api.error.HotelException;
import com.tinqinacademy.hotel.core.contracts.TestService;
import com.tinqinacademy.hotel.persistence.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;
import java.util.UUID;

@RequestMapping("/test")
@RestController
public class TestController {

    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    // Bed

    @PostMapping("/bed/saveBed")
    public ResponseEntity<?> saveBed(@RequestBody Bed input) {
        Bed newBed = testService.saveBed(input);
        return new ResponseEntity<>(newBed, HttpStatus.CREATED);
    }

    @GetMapping("/bed/findByIdBed/{id}")
    public ResponseEntity<?> findByIdBed(@PathVariable UUID id) {
        Bed bed = testService.findByIdBed(id).orElseThrow(() -> new HotelException("no bed on this id"));
        return new ResponseEntity<>(bed, HttpStatus.OK);
    }

    @PutMapping("/bed/updateBed")
    public ResponseEntity<?> updateBed(@RequestBody Bed input) {
        Bed updatedBed = testService.updateBed(input);
        return new ResponseEntity<>(updatedBed, HttpStatus.OK);
    }

    @DeleteMapping("/bed/deleteBed/{id}")
    public ResponseEntity<?> deleteBed(@PathVariable UUID id) {
        testService.deleteBed(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/bed/deleteAllBeds")
    public ResponseEntity<?> deleteAllBeds() {
        testService.deleteAllBeds();
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/bed/findAllBeds")
    public ResponseEntity<?> findAllBeds() {
        List<Bed> beds = testService.findAllBeds();
        return new ResponseEntity<>(beds, HttpStatus.OK);
    }

    @GetMapping("/bed/countsBed")
    public ResponseEntity<?> countBed() {
        long count = testService.countBeds();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    // Room

    @PostMapping("/room/saveRoom")
    public ResponseEntity<?> saveRoom(@RequestBody Room input) {
        Room newRoom = testService.saveRoom(input);
        return new ResponseEntity<>(newRoom, HttpStatus.CREATED);
    }

    @GetMapping("/room/findByIdRoom/{id}")
    public ResponseEntity<?> findByIdRoom(@PathVariable UUID id) {
        Room room = testService.findByIdRoom(id).orElseThrow(() -> new HotelException("no room on this id"));
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @PutMapping("/room/updateRoom")
    public ResponseEntity<?> updateRoom(@RequestBody Room input) {
        Room updatedRoom = testService.updateRoom(input);
        return new ResponseEntity<>(updatedRoom, HttpStatus.OK);
    }

    @DeleteMapping("/room/deleteRoom/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable UUID id) {
        testService.deleteRoom(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/room/deleteAllRooms")
    public ResponseEntity<?> deleteAllRooms() {
        testService.deleteAllRooms();
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/room/findAllRooms")
    public ResponseEntity<?> findAllRooms() {
        List<Room> rooms = testService.findAllRooms();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("/room/countsRoom")
    public ResponseEntity<?> countsRoom() {
        long count = testService.countRooms();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    // User

    @PostMapping("/user/saveUser")
    public ResponseEntity<?> saveUser(@RequestBody User input) {
        User user = testService.saveUser(input);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/user/findByIdUser/{id}")
    public ResponseEntity<?> findByIdUser(@PathVariable UUID id) {
        User user = testService.findByIdUser(id).orElseThrow(() -> new HotelException("no room on this id"));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/user/findAllUsers")
    public ResponseEntity<?> findAllUsers() {
        List<User> users = testService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/user/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        User updateUser = testService.updateUser(user);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping("/user/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        testService.deleteUser(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/user/deleteAllUsers")
    public ResponseEntity<?> deleteAllUsers() {
        testService.deleteAllUsers();
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/countsUser")
    public ResponseEntity<?> countsUser() {
        long count = testService.countUsers();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    // Guest

    @PostMapping("/guest/saveGuest")
    public ResponseEntity<?> saveGuest(@RequestBody Guest input) {
        Guest newGuest = testService.saveGuest(input);
        return new ResponseEntity<>(newGuest, HttpStatus.CREATED);
    }

    @GetMapping("/guest/findByIdGuest/{id}")
    public ResponseEntity<?> findByIdGuest(@PathVariable UUID id) {
        Guest guest = testService.findByIdGuest(id).orElseThrow(() -> new HotelException("no guest with that id found"));
        return new ResponseEntity<>(guest, HttpStatus.OK);
    }

    @GetMapping("/guest/findAllGuests")
    public ResponseEntity<?> findAllGuests() {
        List<Guest> guests = testService.findAllGuests();
        return new ResponseEntity<>(guests, HttpStatus.OK);
    }

    @PutMapping("/guest/updateGuest")
    public ResponseEntity<?> updateGuest(@RequestBody Guest input) {
        Guest guest = testService.updateGuest(input);
        return new ResponseEntity<>(guest, HttpStatus.OK);
    }

    @DeleteMapping("/guest/deleteGuest/{id}")
    public ResponseEntity<?> deleteGuest(@PathVariable UUID id) {
        testService.deleteGuest(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/guest/deleteAllGuests")
    public ResponseEntity<?> deleteAllGuests() {
        testService.deleteAllGuests();
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/guest/countGuests")
    public ResponseEntity<?> countGuests() {
        long count = testService.countGuests();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    // Booking

    @PostMapping("/booking/saveBooking")
    public ResponseEntity<?> saveBooking(@RequestBody Booking input) {
        Booking newBooking = testService.saveBooking(input);
        return new ResponseEntity<>(newBooking, HttpStatus.CREATED);
    }

    @GetMapping("/booking/findByIdBooking/{id}")
    public ResponseEntity<?> findByIdBooking(@PathVariable UUID id) {
        Booking booking = testService.findByIdBooking(id).orElseThrow(() -> new HotelException("no booking with that id found"));
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @PutMapping("/booking/updateBooking")
    public ResponseEntity<?> updateBooking(@RequestBody Booking input) {
        Booking updatedBooking = testService.updateBooking(input);
        return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
    }

    @GetMapping("/booking/findAllBookings")
    public ResponseEntity<?> findAll() {
        List<Booking> bookings = testService.findAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @DeleteMapping("/booking/deleteBooking/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable UUID id) {
        testService.deleteBooking(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/booking/deleteAllBookings")
    public ResponseEntity<?> deleteAllBookings() {
        testService.deleteAllBookings();
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/booking/countsBooking")
    public ResponseEntity<?> countsBooking() {
        long count = testService.countBookings();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @DeleteMapping("/DELETE_ALL_TABLES_DATA")
    public ResponseEntity<?> DELETE_ALL_TABLES_DATA(){
        testService.deleteAllBookings();
        testService.deleteAllGuests();
        testService.deleteAllUsers();
        testService.deleteAllRooms();
        testService.deleteAllBeds();

        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

}
