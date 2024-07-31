package com.tinqinacademy.hotel.rest.controllers;

import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomOperation;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.api.operations.hotel.checkavailableroom.CheckAvailableRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.checkavailableroom.CheckAvailableRoomOperation;
import com.tinqinacademy.hotel.api.operations.hotel.checkavailableroom.CheckAvailableRoomOutput;
import com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory.GetBookingHistoryInput;
import com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory.GetBookingHistoryOperation;
import com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory.GetBookingHistoryOutput;
import com.tinqinacademy.hotel.api.operations.hotel.getroombasicinfo.GetRoomBasicInfoInput;
import com.tinqinacademy.hotel.api.operations.hotel.getroombasicinfo.GetRoomBasicInfoOperation;
import com.tinqinacademy.hotel.api.operations.hotel.getroombasicinfo.GetRoomBasicInfoOutput;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomOutput;
import com.tinqinacademy.hotel.api.operations.hotel.updatepartiallybooking.UpdatePartiallyBookingInput;
import com.tinqinacademy.hotel.api.operations.hotel.updatepartiallybooking.UpdatePartiallyBookingOutput;
import com.tinqinacademy.hotel.core.services.contracts.HotelService;
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

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class HotelController extends BaseController {

    private final HotelService hotelService;

    private final BookRoomOperation bookRoom;
    private final CheckAvailableRoomOperation checkAvailableRoom;
    private final GetBookingHistoryOperation getBookingHistory;
    private final GetRoomBasicInfoOperation getRoomBasicInfo;

    @Operation(summary = "Check room availability for a certain period",
            description = "Check room availability for a certain period")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room is available"),
            @ApiResponse(responseCode = "404", description = "Room not found")})
    @GetMapping(RestApiRoutes.CHECK_ROOM_AVAILABILITY)
    public ResponseEntity<?> checkAvailableRoom(@RequestParam LocalDate startDate,
                                                @RequestParam LocalDate endDate,
                                                @RequestParam String bedSize,
                                                @RequestParam String bathroomType) {
        CheckAvailableRoomInput input = CheckAvailableRoomInput.builder()
                .startDate(startDate)
                .endDate(endDate)
                .bedSize(bedSize)
                .bathroomType(bathroomType)
                .build();

        Either<ErrorsWrapper, CheckAvailableRoomOutput> output = checkAvailableRoom.process(input);
        return handle(output);
    }

    @Operation(summary = "Returns basic info for a room with the specified id",
            description = "Returns basic info for a room with the specified id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room info retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Room not found")})
    @GetMapping(RestApiRoutes.GET_ROOM_INFO)
    public ResponseEntity<?> getRoomBasicInfo(@PathVariable String roomId) {
        GetRoomBasicInfoInput input = GetRoomBasicInfoInput.builder()
                .roomId(roomId)
                .build();

        Either<ErrorsWrapper, GetRoomBasicInfoOutput> output = getRoomBasicInfo.process(input);
        return handle(output);
    }

    @Operation(summary = "Book a room", description = "Book a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Room booked successfully"),
            @ApiResponse(responseCode = "400", description = "Room already booked or bad request"),
            @ApiResponse(responseCode = "404", description = "Room not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PostMapping(RestApiRoutes.BOOK_ROOM)
    public ResponseEntity<?> bookRoom(@PathVariable String roomId,
                                      @RequestBody BookRoomInput input) {
        BookRoomInput updatedInput = input.toBuilder().roomId(roomId).build();

        Either<ErrorsWrapper, BookRoomOutput> output = bookRoom.process(updatedInput);
        return handleWithStatus(output, HttpStatus.CREATED);
    }

    @Operation(summary = "Unbook a room", description = "Unbook a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Room unbooked successfully"),
            @ApiResponse(responseCode = "404", description = "Room booking not found")})
    @DeleteMapping(RestApiRoutes.UNBOOK_ROOM)
    public ResponseEntity<?> deleteBooking(@PathVariable String bookingId) {
        UnbookRoomInput input = UnbookRoomInput.builder().bookingId(bookingId).build();
        UnbookRoomOutput output = hotelService.unbookRoom(input);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @Operation(summary = "Delete all rooms", description = "Delete all rooms")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rooms deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Error deleting rooms")})
    @DeleteMapping(RestApiRoutes.DELETE_ALL_ROOMS)
    public ResponseEntity<?> deleteAllRooms() {
        hotelService.deleteAllRooms();
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Delete all beds", description = "Delete all beds")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Beds deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Error deleting beds")})
    @DeleteMapping(RestApiRoutes.DELETE_ALL_BEDS)
    public ResponseEntity<?> deleteAllBeds() {
        hotelService.deleteAllBeds();
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update partially a booking",
            description = "Update partially a booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking updated successfully"),
            @ApiResponse(responseCode = "400", description = "Error updating the booking")})
    @PatchMapping(RestApiRoutes.UPDATE_PARTIALLY_BOOKING)
    public ResponseEntity<?> updatePartiallyBooking(@PathVariable String bookingId,
                                                    @RequestBody @Valid UpdatePartiallyBookingInput input) {
        UpdatePartiallyBookingInput updatedInput = input.toBuilder()
                .bookingId(bookingId)
                .build();
        UpdatePartiallyBookingOutput output = hotelService.updatePartiallyBooking(updatedInput);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @Operation(summary = "Get booking history",
            description = "Get booking history")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking history retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Error retrieving booking history")})
    @GetMapping(RestApiRoutes.GET_BOOKING_HISTORY)
    public ResponseEntity<?> getBookingHistory(@PathVariable String phoneNumber) {

        GetBookingHistoryInput input = GetBookingHistoryInput.builder()
                .phoneNumber(phoneNumber)
                .build();

        Either<ErrorsWrapper, GetBookingHistoryOutput> output = getBookingHistory.process(input);
        return handle(output);
    }

}