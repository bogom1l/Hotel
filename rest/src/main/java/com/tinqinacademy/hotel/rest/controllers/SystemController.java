package com.tinqinacademy.hotel.rest.controllers;


import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomOperation;
import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.api.operations.system.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.operations.system.deleteroom.DeleteRoomOperation;
import com.tinqinacademy.hotel.api.operations.system.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.operations.system.getallusers.GetAllUsersInput;
import com.tinqinacademy.hotel.api.operations.system.getallusers.GetAllUsersOperation;
import com.tinqinacademy.hotel.api.operations.system.getallusers.GetAllUsersOutput;
import com.tinqinacademy.hotel.api.operations.system.getreport.GetReportInput;
import com.tinqinacademy.hotel.api.operations.system.getreport.GetReportOutput;
import com.tinqinacademy.hotel.api.operations.system.registerguest.RegisterGuestInput;
import com.tinqinacademy.hotel.api.operations.system.registerguest.RegisterGuestOutput;
import com.tinqinacademy.hotel.api.operations.system.updatepartiallyroom.UpdatePartiallyRoomInput;
import com.tinqinacademy.hotel.api.operations.system.updatepartiallyroom.UpdatePartiallyRoomOutput;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomOutput;
import com.tinqinacademy.hotel.core.services.contracts.SystemService;
import com.tinqinacademy.hotel.rest.configurations.RestApiRoutes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.vavr.control.Either;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SystemController extends BaseController {

    private final SystemService systemService;
    private final CreateRoomOperation createRoom;
    private final DeleteRoomOperation deleteRoom;
    private final GetAllUsersOperation getAllUsersByPartialName;

    public SystemController(SystemService systemService, CreateRoomOperation createRoom, DeleteRoomOperation deleteRoom, GetAllUsersOperation getAllUsersByPartialName) {
        this.systemService = systemService;
        this.createRoom = createRoom;
        this.deleteRoom = deleteRoom;
        this.getAllUsersByPartialName = getAllUsersByPartialName;
    }

    @Operation(summary = "Register a guest as room renter",
            description = "Register a guest as room renter by Booking's: roomId, startDate, endDate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Guest registered successfully"),
            @ApiResponse(responseCode = "400", description = "Error registering guest")})
    @PostMapping(RestApiRoutes.REGISTER_GUEST)
    public ResponseEntity<?> registerGuest(@RequestBody RegisterGuestInput input) {
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
    public ResponseEntity<?> createRoom(@RequestBody CreateRoomInput input) {
        Either<ErrorsWrapper, CreateRoomOutput> output = createRoom.process(input);
        return handleWithStatus(output, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a room", description = "Update a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room updated successfully"),
            @ApiResponse(responseCode = "400", description = "Error updating room")})
    @PutMapping(RestApiRoutes.UPDATE_ROOM)
    public ResponseEntity<?> updateRoom(@PathVariable String roomId,
                                        @RequestBody UpdateRoomInput input) {
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
                                                 @RequestBody UpdatePartiallyRoomInput input) {
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

        Either<ErrorsWrapper, DeleteRoomOutput> output = deleteRoom.process(input);
        return handle(output);
    }

    @Operation(summary = "Get all users by partial name", description = "Get all users by partial name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Error retrieving users")})
    @GetMapping(RestApiRoutes.GET_ALL_USERS_BY_PARTIAL_NAME)
    public ResponseEntity<?> getAllUsersByPartialName(@RequestParam(required = false) String partialName) {
        GetAllUsersInput input = GetAllUsersInput.builder().partialName(partialName).build();

        Either<ErrorsWrapper, GetAllUsersOutput> output = getAllUsersByPartialName.process(input);
        return handle(output);
    }

}
