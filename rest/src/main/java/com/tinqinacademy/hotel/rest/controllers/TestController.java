package com.tinqinacademy.hotel.rest.controllers;

import com.tinqinacademy.hotel.api.error.HotelException;
import com.tinqinacademy.hotel.core.contracts.TestService;
import com.tinqinacademy.hotel.persistence.model.Bed;
import com.tinqinacademy.hotel.persistence.model.Room;
import com.tinqinacademy.hotel.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/saveBed")
    public ResponseEntity<?> saveBed(@RequestBody Bed input) {
        Bed newBed = testService.saveBed(input);
        return new ResponseEntity<>(newBed, HttpStatus.CREATED);
    }

    @GetMapping("/findByIdBed/{id}")
    public ResponseEntity<?> findByIdBed(@PathVariable UUID id) {
        Bed bed = testService.findByIdBed(id).orElseThrow(() -> new HotelException("no bed on this id"));
        return new ResponseEntity<>(bed, HttpStatus.OK);
    }

    @PutMapping("/updateBed")
    public ResponseEntity<?> updateBed(@RequestBody Bed input) {
        Bed updatedBed = testService.updateBed(input);
        return new ResponseEntity<>(updatedBed, HttpStatus.OK);
    }

    @DeleteMapping("/deleteBed/{id}")
    public ResponseEntity<?> deleteBed(@PathVariable UUID id) {
        testService.deleteBed(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAllBeds")
    public ResponseEntity<?> deleteAllBeds() {
        testService.deleteAllBeds();
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/findAllBeds")
    public ResponseEntity<?> findAllBeds() {
        List<Bed> beds = testService.findAllBeds();
        return new ResponseEntity<>(beds, HttpStatus.OK);
    }

    @GetMapping("/countsBed")
    public ResponseEntity<?> countBed() {
        long count = testService.countBeds();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    // Room

    @PostMapping("/saveRoom")
    public ResponseEntity<?> saveRoom(@RequestBody Room input) {
        Room newRoom = testService.saveRoom(input);
        return new ResponseEntity<>(newRoom, HttpStatus.CREATED);
    }

    @GetMapping("/findByIdRoom/{id}")
    public ResponseEntity<?> findByIdRoom(@PathVariable UUID id) {
        Room room = testService.findByIdRoom(id).orElseThrow(() -> new HotelException("no room on this id"));
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @PutMapping("/updateRoom")
    public ResponseEntity<?> updateRoom(@RequestBody Room input) {
        Room updatedRoom = testService.updateRoom(input);
        return new ResponseEntity<>(updatedRoom, HttpStatus.OK);
    }

    @DeleteMapping("/deleteRoom/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable UUID id) {
        testService.deleteRoom(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAllRooms")
    public ResponseEntity<?> deleteAllRooms() {
        testService.deleteAllRooms();
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/findAllRooms")
    public ResponseEntity<?> findAllRooms() {
        List<Room> rooms = testService.findAllRooms();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("/countsRoom")
    public ResponseEntity<?> countsRoom() {
        long count = testService.countRooms();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    // User

    @PostMapping("/saveUser")
    public ResponseEntity<?> saveUser(@RequestBody User input) {
        User user = testService.saveUser(input);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/findByIdUser/{id}")
    public ResponseEntity<?> findByIdUser(@PathVariable UUID id) {
        User user = testService.findByIdUser(id).orElseThrow(() -> new HotelException("no room on this id"));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/findAllUsers")
    public ResponseEntity<?> findAllUsers() {
        List<User> users = testService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody User user){
        User updateUser = testService.updateUser(user);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    //delete by id, delete all, count
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        testService.deleteUser(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAllUsers")
    public ResponseEntity<?> deleteAllUsers() {
        testService.deleteAllUsers();
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/countsUser")
    public ResponseEntity<?> countsUser() {
        long count = testService.countUsers();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

}
