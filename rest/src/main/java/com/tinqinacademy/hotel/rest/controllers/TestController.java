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

    @GetMapping("/countBed")
    public ResponseEntity<?> countBed(){
        long count = testService.countBeds();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    // Room

    @PostMapping("/saveroom")
    public ResponseEntity<?> saveRoom(@RequestBody Room input){
        Room newRoom = testService.saveRoom(input);
        return new ResponseEntity<>(newRoom, HttpStatus.CREATED);
    }



    // User

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = testService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


}
