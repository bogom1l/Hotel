package com.tinqinacademy.hotel.controllers;

import com.tinqinacademy.hotel.model.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.model.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.model.operations.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.model.operations.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.model.operations.getroomreport.RegisterReportInput;
import com.tinqinacademy.hotel.model.operations.getroomreport.RegisterReportOutput;
import com.tinqinacademy.hotel.model.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.model.operations.partialupdateroom.PartialUpdateRoomOutput;
import com.tinqinacademy.hotel.model.operations.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.model.operations.registervisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.model.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.model.operations.updateroom.UpdateRoomOutput;
import com.tinqinacademy.hotel.services.contracts.SystemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system")
public class SystemController {

    private final SystemService systemService;

    @Autowired
    public SystemController(SystemService systemService) {
        this.systemService = systemService;
    }

    @Operation(summary = "Register a visitor", description = "Registers a visitor as room renter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Visitor registered successfully"),
            @ApiResponse(responseCode = "400", description = "Error registering visitor")
    })
    @PostMapping("/register") // POST /system/register
    public ResponseEntity<?> registerVisitor(@RequestBody RegisterVisitorInput input) {

        RegisterVisitorOutput output = systemService.registerVisitor(input);

        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

    @Operation(summary = "Provides a room report", description = "Generates and returns a report based on various criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room report generated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    @GetMapping("/register") // GET system/register
    public ResponseEntity<?> getRoomReport(@RequestParam String startDate,
                                           @RequestParam String endDate,
                                           @RequestParam String firstName,
                                           @RequestParam String lastName,
                                           @RequestParam String phoneNo,
                                           @RequestParam String idCardNo,
                                           @RequestParam String idCardValidity,
                                           @RequestParam String idCardIssueAuthority,
                                           @RequestParam String idCardIssueDate,
                                           @RequestParam String roomNo) {

        RegisterReportInput input = RegisterReportInput.builder()
                .startDate(startDate)
                .endDate(endDate)
                .fistName(firstName)
                .lastName(lastName)
                .phoneNo(phoneNo)
                .idCardNo(idCardNo)
                .idCardValidity(idCardValidity)
                .idCardIssueAuthority(idCardIssueAuthority)
                .idCardIssueDate(idCardIssueDate)
                .roomNo(roomNo)
                .build();

        RegisterReportOutput output = systemService.registerReport(input);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @Operation(summary = "Ceate a room", description = "Admin creates a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2010", description = "Room created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    @PostMapping("/room") // POST /system/room
    public ResponseEntity<?> createRoom(@RequestBody CreateRoomInput input){
        CreateRoomOutput output = systemService.createRoom(input);

        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a room", description = "Admin updates a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2010", description = "Room updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    @PutMapping("/room/{roomId}") // POST /system/room/{roomId}
    public ResponseEntity<?> updateRoom(//@PathVariable String roomId,
                                        @RequestBody UpdateRoomInput input){
//        UpdateRoomInput updatedInput = input.toBuilder()
//                .roomId(roomId)
//                .build();

        UpdateRoomOutput output = systemService.updateRoom(input); //updatedInput

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @Operation(summary = "Partially update a room", description = "Admin partially updates a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2010", description = "Room updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    @PatchMapping("/room/{roomId}") // PATCH /system/room/{roomId}
    public ResponseEntity<?> partialUpdateRoom(@RequestBody PartialUpdateRoomInput input){

        //TODO: maybe same changes as the method above
        PartialUpdateRoomOutput output = systemService.partialUpdateRoom(input);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @Operation(summary = "Delete a room", description = "Admin deletes a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Room deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    @DeleteMapping("/room/{roomId}")
    public ResponseEntity<?> deleteRoom(@PathVariable("roomId") String id){

        DeleteRoomInput input = DeleteRoomInput.builder()
                .id(id)
                .build();

        DeleteRoomOutput output = systemService.deleteRoom(input);

        return new ResponseEntity<>(output, HttpStatus.NO_CONTENT);
    }


}
