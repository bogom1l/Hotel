package com.tinqinacademy.hotel.rest.controllers;

import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.operations.getroomreport.RegisterReportInput;
import com.tinqinacademy.hotel.api.operations.getroomreport.RegisterReportOutput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomOutput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomOutput;
import com.tinqinacademy.hotel.core.contracts.SystemService;
import com.tinqinacademy.hotel.rest.configurations.RestApiRoutes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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

    @Operation(summary = "Register a visitor as room renter",
            description = "Register a visitor as room renter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Visitor registered successfully"),
            @ApiResponse(responseCode = "400", description = "Error registering visitor")})
    @PostMapping(RestApiRoutes.REGISTER_VISITOR) // POST /system/register
    public ResponseEntity<?> registerVisitor(@RequestBody @Valid RegisterVisitorInput input) {
        RegisterVisitorOutput output = systemService.registerVisitor(input);

        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

    @Operation(summary = "Provides a room report based on various criteria",
            description = "Provides a room report based on various criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room report generated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")})
    @GetMapping(RestApiRoutes.GET_ROOM_REPORT) // GET system/register
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
                .startDate(startDate).
                endDate(endDate)
                .fistName(firstName)
                .lastName(lastName)
                .phoneNo(phoneNo)
                .idCardNo(idCardNo)
                .idCardValidity(idCardValidity).
                idCardIssueAuthority(idCardIssueAuthority)
                .idCardIssueDate(idCardIssueDate)
                .roomNo(roomNo)
                .build();

        RegisterReportOutput output = systemService.registerReport(input);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @Operation(summary = "Create a room", description = "Create a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Room created successfully"),
            @ApiResponse(responseCode = "400", description = "Error creating room")})
    @PostMapping(RestApiRoutes.CREATE_ROOM) // POST /system/room
    public ResponseEntity<?> createRoom(@RequestBody @Valid CreateRoomInput input) {
        CreateRoomOutput output = systemService.createRoom(input);

        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a room", description = "Update a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room updated successfully"),
            @ApiResponse(responseCode = "400", description = "Error updating room")})
    @PutMapping(RestApiRoutes.UPDATE_ROOM) // PUT /system/room/{roomId}
    public ResponseEntity<?> updateRoom(@PathVariable String roomId,
                                        @RequestBody @Valid UpdateRoomInput input) {
        UpdateRoomInput updatedInput = input.toBuilder().roomId(roomId).build();

        UpdateRoomOutput output = systemService.updateRoom(updatedInput);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @Operation(summary = "Update partially a room", description = "Update partially a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")})
    @PatchMapping(RestApiRoutes.UPDATE_PARTIALLY_ROOM) // PATCH /system/room/{roomId}
    public ResponseEntity<?> partialUpdateRoom(@PathVariable String roomId,
                                               @RequestBody @Valid PartialUpdateRoomInput input) {
        PartialUpdateRoomInput updatedInput = input.toBuilder().roomId(roomId).build();

        PartialUpdateRoomOutput output = systemService.partialUpdateRoom(updatedInput);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @Operation(summary = "Delete a room", description = "Delete a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Room deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")})
    @DeleteMapping(RestApiRoutes.DELETE_ROOM) // DELETE /system/room/{roomId}
    public ResponseEntity<?> deleteRoom(@PathVariable("roomId") String id) {
        DeleteRoomInput input = DeleteRoomInput.builder().id(id).build();

        DeleteRoomOutput output = systemService.deleteRoom(input);

        return new ResponseEntity<>(output, HttpStatus.NO_CONTENT);
    }


}