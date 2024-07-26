package com.tinqinacademy.hotel.rest.controllers;


import com.tinqinacademy.hotel.core.contracts.SystemService;
import com.tinqinacademy.hotel.persistence.model.operations.system.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.persistence.model.operations.system.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.persistence.model.operations.system.getroomreport.GetReportInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.getroomreport.GetReportOutput;
import com.tinqinacademy.hotel.persistence.model.operations.system.registerguest.RegisterGuestInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.registerguest.RegisterGuestOutput;
import com.tinqinacademy.hotel.persistence.model.operations.system.updatepartiallyroom.UpdatePartiallyRoomInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.updatepartiallyroom.UpdatePartiallyRoomOutput;
import com.tinqinacademy.hotel.persistence.model.operations.system.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.updateroom.UpdateRoomOutput;
import com.tinqinacademy.hotel.rest.configurations.RestApiRoutes;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SystemController {

    private final SystemService systemService;

    @Autowired
    public SystemController(SystemService systemService) {
        this.systemService = systemService;
    }

    @Operation(summary = "Register a guest as room renter")
    @PostMapping(RestApiRoutes.REGISTER_GUEST)
    public ResponseEntity<?> registerGuest(@RequestBody @Valid RegisterGuestInput input) {
        RegisterGuestOutput output = systemService.registerGuest(input);

        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

    // TODO: refactor, make the fields optional
    @Operation(summary = "Provides a room report based on various criteria")
    @GetMapping(RestApiRoutes.GET_REPORT)
    public ResponseEntity<?> getReport(@RequestParam String startDate,
                                       @RequestParam String endDate,
                                       @RequestParam String firstName,
                                       @RequestParam String lastName,
                                       @RequestParam String phoneNo,
                                       @RequestParam String idCardNo,
                                       @RequestParam String idCardValidity,
                                       @RequestParam String idCardIssueAuthority,
                                       @RequestParam String idCardIssueDate,
                                       @RequestParam String roomNo) {

        GetReportInput input = GetReportInput.builder()
                .startDate(startDate)
                .endDate(endDate)
                .firstName(firstName)
                .lastName(lastName)
                .phoneNo(phoneNo)
                .idCardNo(idCardNo)
                .idCardValidity(idCardValidity)
                .idCardIssueAuthority(idCardIssueAuthority)
                .idCardIssueDate(idCardIssueDate)
                .roomNo(roomNo)
                .build();

        GetReportOutput output = systemService.getReport(input);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @Operation(summary = "Create a room")
    @PostMapping(RestApiRoutes.CREATE_ROOM)
    public ResponseEntity<?> createRoom(@RequestBody @Valid CreateRoomInput input) {
        CreateRoomOutput output = systemService.createRoom(input);

        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a room")
    @PutMapping(RestApiRoutes.UPDATE_ROOM)
    public ResponseEntity<?> updateRoom(@PathVariable String roomId,
                                        @RequestBody @Valid UpdateRoomInput input) {
        UpdateRoomInput updatedInput = input.toBuilder().roomId(roomId).build();

        UpdateRoomOutput output = systemService.updateRoom(updatedInput);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @Operation(summary = "Update partially a room")
    @PatchMapping(RestApiRoutes.UPDATE_PARTIALLY_ROOM)
    public ResponseEntity<?> updatePartiallyRoom(@PathVariable String roomId,
                                                 @RequestBody @Valid UpdatePartiallyRoomInput input) {

        UpdatePartiallyRoomInput updatedInput = input.toBuilder().roomId(roomId).build();

        UpdatePartiallyRoomOutput output = systemService.updatePartiallyRoom(updatedInput);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @Operation(summary = "Delete a room")
    @DeleteMapping(RestApiRoutes.DELETE_ROOM)
    public ResponseEntity<?> deleteRoom(@PathVariable("roomId") String id) {
        DeleteRoomInput input = DeleteRoomInput.builder().id(id).build();

        DeleteRoomOutput output = systemService.deleteRoom(input);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @Operation(summary = "Delete all users")
    @DeleteMapping("/api/v1/system/deleteAllUsers")
    public ResponseEntity<?> deleteAllUsers() {
        systemService.deleteAllUsers();
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Delete all guests")
    @DeleteMapping("/api/v1/system/deleteAllGuests")
    public ResponseEntity<?> deleteAllGuests() {
        systemService.deleteAllGuests();
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Delete all bookings")
    @DeleteMapping("/api/v1/system/deleteAllBookings")
    public ResponseEntity<?> deleteAllBookings() {
        systemService.deleteAllBookings();
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

/*
    
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

*/
}
