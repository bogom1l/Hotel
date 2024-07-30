package com.tinqinacademy.hotel.rest.controllers;


import com.tinqinacademy.hotel.core.contracts.SystemService;
import com.tinqinacademy.hotel.persistence.model.operations.system.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.persistence.model.operations.system.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.persistence.model.operations.system.getallusers.GetAllUsersInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.getallusers.GetAllUsersOutput;
import com.tinqinacademy.hotel.persistence.model.operations.system.getreport.GetReportInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.getreport.GetReportOutput;
import com.tinqinacademy.hotel.persistence.model.operations.system.registerguest.RegisterGuestInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.registerguest.RegisterGuestOutput;
import com.tinqinacademy.hotel.persistence.model.operations.system.updatepartiallyroom.UpdatePartiallyRoomInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.updatepartiallyroom.UpdatePartiallyRoomOutput;
import com.tinqinacademy.hotel.persistence.model.operations.system.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.updateroom.UpdateRoomOutput;
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
public class SystemController {

    private final SystemService systemService;

    @Autowired
    public SystemController(SystemService systemService) {
        this.systemService = systemService;
    }

    @Operation(summary = "Register a guest as room renter",
            description = "Register a guest as room renter by Booking's: roomId, startDate, endDate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Guest registered successfully"),
            @ApiResponse(responseCode = "400", description = "Error registering guest")})
    @PostMapping(RestApiRoutes.REGISTER_GUEST)
    public ResponseEntity<?> registerGuest(@RequestBody @Valid RegisterGuestInput input) {
        RegisterGuestOutput output = systemService.registerGuest(input);

        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

    @Operation(summary = "Provides a report based on various criteria",
            description = "Provides a report based on various criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Report generated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")})
    @GetMapping(RestApiRoutes.GET_REPORT)
    public ResponseEntity<?> getReport(@RequestParam(required = false) String startDate,
                                       @RequestParam(required = false) String endDate,
                                       @RequestParam(required = false) String firstName,
                                       @RequestParam(required = false) String lastName,
                                       @RequestParam(required = false) String phoneNumber,
                                       @RequestParam(required = false) String idCardNumber,
                                       @RequestParam(required = false) String idCardValidity,
                                       @RequestParam(required = false) String idCardIssueAuthority,
                                       @RequestParam(required = false) String idCardIssueDate,
                                       @RequestParam(required = false) String roomNumber) {
        GetReportInput input = GetReportInput.builder()
                .startDate(startDate)
                .endDate(endDate)
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .idCardNumber(idCardNumber)
                .idCardValidity(idCardValidity)
                .idCardIssueAuthority(idCardIssueAuthority)
                .idCardIssueDate(idCardIssueDate)
                .roomNumber(roomNumber)
                .build();

        GetReportOutput output = systemService.getReport(input);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @Operation(summary = "Create a room", description = "Create a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Room created successfully"),
            @ApiResponse(responseCode = "400", description = "Error creating room")})
    @PostMapping(RestApiRoutes.CREATE_ROOM)
    public ResponseEntity<?> createRoom(@RequestBody @Valid CreateRoomInput input) {
        CreateRoomOutput output = systemService.createRoom(input);

        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a room", description = "Update a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room updated successfully"),
            @ApiResponse(responseCode = "400", description = "Error updating room")})
    @PutMapping(RestApiRoutes.UPDATE_ROOM)
    public ResponseEntity<?> updateRoom(@PathVariable String roomId,
                                        @RequestBody @Valid UpdateRoomInput input) {
        UpdateRoomInput updatedInput = input.toBuilder().roomId(roomId).build();

        UpdateRoomOutput output = systemService.updateRoom(updatedInput);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @Operation(summary = "Update partially a room", description = "Update partially a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room updated successfully"),
            @ApiResponse(responseCode = "400", description = "Error updating room")})
    @PatchMapping(RestApiRoutes.UPDATE_PARTIALLY_ROOM)
    public ResponseEntity<?> updatePartiallyRoom(@PathVariable String roomId,
                                                 @RequestBody @Valid UpdatePartiallyRoomInput input) {
        UpdatePartiallyRoomInput updatedInput = input.toBuilder().roomId(roomId).build();

        UpdatePartiallyRoomOutput output = systemService.updatePartiallyRoom(updatedInput);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @Operation(summary = "Delete a room", description = "Delete a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Error deleting room")})
    @DeleteMapping(RestApiRoutes.DELETE_ROOM)
    public ResponseEntity<?> deleteRoom(@PathVariable("roomId") String id) {
        DeleteRoomInput input = DeleteRoomInput.builder().id(id).build();

        DeleteRoomOutput output = systemService.deleteRoom(input);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @Operation(summary = "Delete all users", description = "Delete all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Error deleting users")})
    @DeleteMapping(RestApiRoutes.DELETE_ALL_USERS)
    public ResponseEntity<?> deleteAllUsers() {
        systemService.deleteAllUsers();
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Delete all guests", description = "Delete all guests")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Guests deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Error deleting guests")})
    @DeleteMapping(RestApiRoutes.DELETE_ALL_GUESTS)
    public ResponseEntity<?> deleteAllGuests() {
        systemService.deleteAllGuests();
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Delete all bookings", description = "Delete all bookings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookings deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Error deleting bookings")})
    @DeleteMapping(RestApiRoutes.DELETE_ALL_BOOKINGS)
    public ResponseEntity<?> deleteAllBookings(/*todo inputmodel*/) {
        systemService.deleteAllBookings();
        return new ResponseEntity<>(/*todo outputmodel*/null, HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get all users by partial name", description = "Get all users by partial name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Error retrieving users")})
    @GetMapping(RestApiRoutes.GET_ALL_USERS_BY_PARTIAL_NAME)
    public ResponseEntity<?> getAllUsersByPartialName(@RequestParam(required = false) String partialName) {

        GetAllUsersInput input = GetAllUsersInput.builder().partialName(partialName).build();
        GetAllUsersOutput output = systemService.getAllUsersByPartialName(input);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

}
