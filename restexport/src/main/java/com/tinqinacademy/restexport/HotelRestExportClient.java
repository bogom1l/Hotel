package com.tinqinacademy.restexport;

import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.checkavailableroom.CheckAvailableRoomOutput;
import com.tinqinacademy.hotel.api.operations.hotel.updatepartiallybooking.UpdatePartiallyBookingInput;
import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.system.registerguest.RegisterGuestInput;
import com.tinqinacademy.hotel.api.operations.system.updatepartiallyroom.UpdatePartiallyRoomInput;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.restroutes.RestApiRoutes;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@FeignClient(name = "hotel")
@Headers("Content-Type: application/json")
public interface HotelRestExportClient {

    @GetMapping(RestApiRoutes.GET_ROOM_INFO)
    ResponseEntity<?> getRoomBasicInfo(@PathVariable String roomId);

    @GetMapping(RestApiRoutes.CHECK_ROOM_AVAILABILITY)
    CheckAvailableRoomOutput checkAvailableRoom(@RequestParam(required = false) LocalDate startDate,
                                                @RequestParam(required = false) LocalDate endDate,
                                                @RequestParam(required = false) String bedSize,
                                                @RequestParam(required = false) String bathroomType);

    @PostMapping(RestApiRoutes.BOOK_ROOM)
    ResponseEntity<?> bookRoom(@PathVariable String roomId, @RequestBody BookRoomInput input);

    @DeleteMapping(RestApiRoutes.UNBOOK_ROOM)
    ResponseEntity<?> unbookRoom(@PathVariable String bookingId);

    @PatchMapping(RestApiRoutes.UPDATE_PARTIALLY_BOOKING)
    ResponseEntity<?> updatePartiallyBooking(@PathVariable String bookingId,
                                             @RequestBody UpdatePartiallyBookingInput input);

    @GetMapping(RestApiRoutes.GET_BOOKING_HISTORY)
    ResponseEntity<?> getBookingHistory(@PathVariable String phoneNumber);

    @PostMapping(RestApiRoutes.REGISTER_GUEST)
    ResponseEntity<?> registerGuest(@RequestBody RegisterGuestInput input);

    @GetMapping(RestApiRoutes.GET_REPORT)
    ResponseEntity<?> getReport(@RequestParam(required = false) String startDate,
                                @RequestParam(required = false) String endDate,
                                @RequestParam(required = false) String firstName,
                                @RequestParam(required = false) String lastName,
                                @RequestParam(required = false) String phoneNumber,
                                @RequestParam(required = false) String idCardNumber,
                                @RequestParam(required = false) String idCardValidity,
                                @RequestParam(required = false) String idCardIssueAuthority,
                                @RequestParam(required = false) String idCardIssueDate,
                                @RequestParam(required = false) String roomNumber);

    @PostMapping(RestApiRoutes.CREATE_ROOM)
    ResponseEntity<?> createRoom(@RequestBody CreateRoomInput input);

    @PutMapping(RestApiRoutes.UPDATE_ROOM)
    ResponseEntity<?> updateRoom(@PathVariable String roomId, @RequestBody UpdateRoomInput input);

    @PatchMapping(RestApiRoutes.UPDATE_PARTIALLY_ROOM)
    ResponseEntity<?> updatePartiallyRoom(@PathVariable String roomId, @RequestBody UpdatePartiallyRoomInput input);

    @DeleteMapping(RestApiRoutes.DELETE_ROOM)
    ResponseEntity<?> deleteRoom(@PathVariable("roomId") String id);

    @GetMapping(RestApiRoutes.GET_ALL_USERS_BY_PARTIAL_NAME)
    ResponseEntity<?> getAllUsersByPartialName(@RequestParam(required = false) String partialName);
}
