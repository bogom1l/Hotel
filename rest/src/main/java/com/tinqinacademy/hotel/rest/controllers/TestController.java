package com.tinqinacademy.hotel.rest.controllers;

import com.tinqinacademy.hotel.api.error.HotelException;
import com.tinqinacademy.hotel.core.contracts.TestService;
import com.tinqinacademy.hotel.persistence.model.Bed;
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

    @GetMapping("/getallusers")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = testService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/getallbeds")
    public ResponseEntity<?> getAllBeds() {
        List<Bed> beds = testService.findAllBeds();
        return new ResponseEntity<>(beds, HttpStatus.OK);
    }

    @PostMapping("/savebed")
    public ResponseEntity<?> saveBed(@RequestBody Bed input) {
        Bed bed = Bed.builder()
                .id(UUID.randomUUID())
                .capacity(input.getCapacity())
                .bedSize(input.getBedSize())
                .build();

        Bed newBed = testService.saveBed(bed);
        return new ResponseEntity<>(newBed, HttpStatus.CREATED);
    }

    @GetMapping("/findbyidbed/{id}")
    public ResponseEntity<?> findByIdBed(@PathVariable UUID id) {
        Bed bed = testService.findByIdBed(id).orElseThrow(() -> new HotelException("no bed on this id"));
        return new ResponseEntity<>(bed, HttpStatus.OK);
    }

    @PutMapping("/updatebed")
    public ResponseEntity<?> updateBed(@RequestBody Bed input) {
        Bed updatedBed = testService.updateBed(input);
        return new ResponseEntity<>(updatedBed, HttpStatus.OK);
    }

    @DeleteMapping("/deletebed/{id}")
    public ResponseEntity<?> deleteBed(@PathVariable UUID id) {
        testService.deleteByIdBed(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAllBed")
    public ResponseEntity<?> deleteAllBed() {
        testService.deleteAll();
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    // TODO: findAllBeds, countBeds

}
