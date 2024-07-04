package com.tinqinacademy.hotel.controllers;

import com.tinqinacademy.hotel.model.GetRoom;
import com.tinqinacademy.hotel.model.RoomInput;
import com.tinqinacademy.hotel.model.RoomOutput;
import com.tinqinacademy.hotel.services.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/home")
@RestController
public class HotelController {

    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Operation(summary = "Books a room", description = "Book must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Room booked successfully"),
            @ApiResponse(responseCode = "400", description = "Already booked"),
            @ApiResponse(responseCode = "404", description = "Error not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/book")
    public ResponseEntity<?> bookRoom(){
        String result = hotelService.bookRoom();
        return new ResponseEntity<>(result, HttpStatus.OK); //TODO ? HttpStatus.CREATED
    }

    @Operation(summary = "Checks a room availability", description = "Room must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room is available"),
            @ApiResponse(responseCode = "400", description = "Room is not available")
    })
    @GetMapping("/check")
    public ResponseEntity<?> checkRoom(){
        Boolean result = hotelService.isAvailable();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Adds a room", description = "Adding room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Room is added successfully"),
            @ApiResponse(responseCode = "400", description = "Room failed adding")
    })
    @PostMapping("/add")
    public ResponseEntity<?> addRoom(@RequestBody RoomInput input){
        RoomOutput output = hotelService.addRoom(input);
        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

    @Operation(summary = "Remove a room", description = "Remove room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room is removed successfully"),
            @ApiResponse(responseCode = "400", description = "Room failed removing")
    })
    @DeleteMapping("/remove")
    public ResponseEntity<?> removeRoom(){
        String result = hotelService.removeRoom();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Edit a room", description = "Edit room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Roomed edited successfully"),
            @ApiResponse(responseCode = "400",description = "Room failed editing")
    })
    @PutMapping("/edit")
    public ResponseEntity<?> editRoom(){
        String result = hotelService.editRoom();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // localhost:8080/home/get/3?bedType=single
    @GetMapping("/get/{floor}")
    public ResponseEntity<?> getRoom(@RequestParam String bedType, @PathVariable Integer floor){
        GetRoom room = GetRoom.builder()
                .bedType(bedType)
                .floor(floor)
                .build();
        String result = hotelService.getRoom(room);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
