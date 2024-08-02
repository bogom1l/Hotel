package com.tinqinacademy.hotel.rest.controllers;


import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomOperation;
import com.tinqinacademy.hotel.api.operations.system.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.operations.system.deleteroom.DeleteRoomOperation;
import com.tinqinacademy.hotel.api.operations.system.getallusers.GetAllUsersInput;
import com.tinqinacademy.hotel.api.operations.system.getallusers.GetAllUsersOperation;
import com.tinqinacademy.hotel.api.operations.system.getreport.GetReportInput;
import com.tinqinacademy.hotel.api.operations.system.getreport.GetReportOperation;
import com.tinqinacademy.hotel.api.operations.system.registerguest.RegisterGuestInput;
import com.tinqinacademy.hotel.api.operations.system.registerguest.RegisterGuestOperation;
import com.tinqinacademy.hotel.api.operations.system.updatepartiallyroom.UpdatePartiallyRoomInput;
import com.tinqinacademy.hotel.api.operations.system.updatepartiallyroom.UpdatePartiallyRoomOperation;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomOperation;
import com.tinqinacademy.hotel.api.restroutes.RestApiRoutes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SystemController extends BaseController {
    private final GetReportOperation getReport;
    private final GetAllUsersOperation getAllUsersByPartialName;
    private final RegisterGuestOperation registerGuest;
    private final CreateRoomOperation createRoom;
    private final UpdateRoomOperation updateRoom;
    private final UpdatePartiallyRoomOperation updatePartiallyRoom;
    private final DeleteRoomOperation deleteRoom;

    public SystemController(GetReportOperation getReport, GetAllUsersOperation getAllUsersByPartialName, RegisterGuestOperation registerGuest, CreateRoomOperation createRoom, UpdateRoomOperation updateRoom, UpdatePartiallyRoomOperation updatePartiallyRoom, DeleteRoomOperation deleteRoom) {
        this.getReport = getReport;
        this.getAllUsersByPartialName = getAllUsersByPartialName;
        this.registerGuest = registerGuest;
        this.createRoom = createRoom;
        this.updateRoom = updateRoom;
        this.updatePartiallyRoom = updatePartiallyRoom;
        this.deleteRoom = deleteRoom;
    }

    @Operation(summary = "Register a guest as room renter",
            description = "Register a guest as room renter by Booking's: roomId, startDate, endDate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Guest registered successfully"),
            @ApiResponse(responseCode = "400", description = "Error registering guest")})
    @PostMapping(RestApiRoutes.REGISTER_GUEST)
    public ResponseEntity<?> registerGuest(@RequestBody RegisterGuestInput input) {

        return handleWithStatus(registerGuest.process(input), HttpStatus.CREATED);
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

        return handle(getReport.process(input));
    }

    @Operation(summary = "Create a room", description = "Create a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Room created successfully"),
            @ApiResponse(responseCode = "400", description = "Error creating room")})
    @PostMapping(RestApiRoutes.CREATE_ROOM)
    public ResponseEntity<?> createRoom(@RequestBody CreateRoomInput input) {

        return handleWithStatus(createRoom.process(input), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a room", description = "Update a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room updated successfully"),
            @ApiResponse(responseCode = "400", description = "Error updating room")})
    @PutMapping(RestApiRoutes.UPDATE_ROOM)
    public ResponseEntity<?> updateRoom(@PathVariable String roomId,
                                        @RequestBody UpdateRoomInput input) {
        UpdateRoomInput updatedInput = input.toBuilder().roomId(roomId).build();

        return handle(updateRoom.process(updatedInput));
    }

    @Operation(summary = "Update partially a room", description = "Update partially a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room updated successfully"),
            @ApiResponse(responseCode = "400", description = "Error updating room")})
    @PatchMapping(RestApiRoutes.UPDATE_PARTIALLY_ROOM)
    public ResponseEntity<?> updatePartiallyRoom(@PathVariable String roomId,
                                                 @RequestBody UpdatePartiallyRoomInput input) {
        UpdatePartiallyRoomInput updatedInput = input.toBuilder().roomId(roomId).build();

        return handle(updatePartiallyRoom.process(updatedInput));
    }

    @Operation(summary = "Delete a room", description = "Delete a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Error deleting room")})
    @DeleteMapping(RestApiRoutes.DELETE_ROOM)
    public ResponseEntity<?> deleteRoom(@PathVariable("roomId") String id) {
        DeleteRoomInput input = DeleteRoomInput.builder().id(id).build();

        return handle(deleteRoom.process(input));
    }

    @Operation(summary = "Get all users by partial name", description = "Get all users by partial name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Error retrieving users")})
    @GetMapping(RestApiRoutes.GET_ALL_USERS_BY_PARTIAL_NAME)
    public ResponseEntity<?> getAllUsersByPartialName(@RequestParam(required = false) String partialName) {
        GetAllUsersInput input = GetAllUsersInput.builder().partialName(partialName).build();

        return handle(getAllUsersByPartialName.process(input));
    }

}
