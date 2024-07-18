package com.tinqinacademy.hotel.rest.controllers;

import com.tinqinacademy.hotel.core.contracts.TestService;
import com.tinqinacademy.hotel.persistence.models.Bed;
import com.tinqinacademy.hotel.persistence.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

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
        try {
            List<Bed> beds = testService.findAllBeds();
            return new ResponseEntity<>(beds, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() + "\n" + e.getCause());
        }
    }

    @PostMapping("/addbed")
    public ResponseEntity<?> addBed(@RequestBody Bed input) {
        try {
            Bed bed = Bed.builder()
                    .id(UUID.randomUUID())
                    .capacity(input.getCapacity())
                    .bedSize(input.getBedSize())
                    .build();

            testService.addBed(bed);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() + "\n" + e.getCause());
        }

    }

}
