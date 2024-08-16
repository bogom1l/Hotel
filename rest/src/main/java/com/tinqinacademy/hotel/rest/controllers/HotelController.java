package com.tinqinacademy.hotel.rest.controllers;

import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomOperation;
import com.tinqinacademy.hotel.api.operations.hotel.checkavailableroom.CheckAvailableRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.checkavailableroom.CheckAvailableRoomOperation;
import com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory.GetBookingHistoryInput;
import com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory.GetBookingHistoryOperation;
import com.tinqinacademy.hotel.api.operations.hotel.getroombasicinfo.GetRoomBasicInfoInput;
import com.tinqinacademy.hotel.api.operations.hotel.getroombasicinfo.GetRoomBasicInfoOperation;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomOperation;
import com.tinqinacademy.hotel.api.operations.hotel.updatepartiallybooking.UpdatePartiallyBookingInput;
import com.tinqinacademy.hotel.api.operations.hotel.updatepartiallybooking.UpdatePartiallyBookingOperation;
import com.tinqinacademy.hotel.api.restroutes.RestApiRoutes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class HotelController extends BaseController {
    private final GetRoomBasicInfoOperation getRoomBasicInfo;
    private final CheckAvailableRoomOperation checkAvailableRoom;
    private final BookRoomOperation bookRoom;
    private final UnbookRoomOperation unbookRoom;
//    private final UpdatePartiallyBookingOperation updatePartiallyBooking;
//    private final GetBookingHistoryOperation getBookingHistory;

    @Operation(summary = "Check room availability for a certain period",
            description = "Check room availability for a certain period")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room is available"),
            @ApiResponse(responseCode = "404", description = "Room not found")})
    @GetMapping(RestApiRoutes.CHECK_ROOM_AVAILABILITY)
    public ResponseEntity<?> checkAvailableRoom(@RequestParam(required = false) LocalDate startDate,
                                                @RequestParam(required = false) LocalDate endDate,
                                                @RequestParam(required = false) String bedSize,
                                                @RequestParam(required = false) String bathroomType) {
        CheckAvailableRoomInput input = CheckAvailableRoomInput.builder()
                .startDate(startDate)
                .endDate(endDate)
                .bedSize(bedSize)
                .bathroomType(bathroomType)
                .build();

        return handle(checkAvailableRoom.process(input));
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

        return handle(getRoomBasicInfo.process(input));
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

        return handleWithStatus(bookRoom.process(updatedInput), HttpStatus.CREATED);
    }

    @Operation(summary = "Unbook a room", description = "Unbook a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Room unbooked successfully"),
            @ApiResponse(responseCode = "404", description = "Room booking not found")})
    @DeleteMapping(RestApiRoutes.UNBOOK_ROOM)
    public ResponseEntity<?> unbookRoom(@PathVariable String bookingId,
                                        @RequestBody UnbookRoomInput input) {
        UnbookRoomInput updatedInput = input.toBuilder().bookingId(bookingId).build();

        return handle(unbookRoom.process(updatedInput));
    }
//
//    @Operation(summary = "Update partially a booking",
//            description = "Update partially a booking")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Booking updated successfully"),
//            @ApiResponse(responseCode = "400", description = "Error updating the booking")})
//    @PatchMapping(RestApiRoutes.UPDATE_PARTIALLY_BOOKING)
//    public ResponseEntity<?> updatePartiallyBooking(@PathVariable String bookingId,
//                                                    @RequestBody UpdatePartiallyBookingInput input) {
//        UpdatePartiallyBookingInput updatedInput = input.toBuilder()
//                .bookingId(bookingId)
//                .build();
//
//        return handle(updatePartiallyBooking.process(updatedInput));
//    }
//
//    @Operation(summary = "Get booking history",
//            description = "Get booking history")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Booking history retrieved successfully"),
//            @ApiResponse(responseCode = "400", description = "Error retrieving booking history")})
//    @GetMapping(RestApiRoutes.GET_BOOKING_HISTORY)
//    public ResponseEntity<?> getBookingHistory(@PathVariable String phoneNumber) {
//        GetBookingHistoryInput input = GetBookingHistoryInput.builder()
//                .phoneNumber(phoneNumber)
//                .build();
//
//        return handle(getBookingHistory.process(input));
//    }

}