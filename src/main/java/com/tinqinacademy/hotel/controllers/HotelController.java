package com.tinqinacademy.hotel.controllers;

import com.tinqinacademy.hotel.model.*;
import com.tinqinacademy.hotel.model.enums.BedSize;
import com.tinqinacademy.hotel.model.input.GetRoomInput;
import com.tinqinacademy.hotel.services.contracts.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/hotel")
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
        return new ResponseEntity<>(result, HttpStatus.OK);
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


    @GetMapping("/rooms")
    public ResponseEntity<?> getRooms(@RequestParam LocalDate startDate,
                                      @RequestParam LocalDate endDate,
                                      @RequestParam Integer bedCount,
                                      @RequestParam String bedSize,
                                      @RequestParam String bathroomType){
        GetRoomInput input = GetRoomInput.builder()
                .startDate(startDate)
                .endDate(endDate)
                .bedCount(bedCount)
                .bedSize(bedSize)
                // TODO: maybe it shouldn't work when i give it wrong enum data
                //  .bedSize(BedSize.getByCode(bedSize).toString())
                .bathroomType(bathroomType) // TODO: same here
                .build();

        List<String> result = hotelService.getRooms(input);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
