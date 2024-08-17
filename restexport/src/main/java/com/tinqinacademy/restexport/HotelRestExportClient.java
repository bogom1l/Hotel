package com.tinqinacademy.restexport;

import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.api.operations.hotel.checkavailableroom.CheckAvailableRoomOutput;
import com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory.GetBookingHistoryInput;
import com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory.GetBookingHistoryOutput;
import com.tinqinacademy.hotel.api.operations.hotel.getroombasicinfo.GetRoomBasicInfoOutput;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomOutput;
import com.tinqinacademy.hotel.api.operations.hotel.updatepartiallybooking.UpdatePartiallyBookingInput;
import com.tinqinacademy.hotel.api.operations.hotel.updatepartiallybooking.UpdatePartiallyBookingOutput;
import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.api.operations.system.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.operations.system.getreport.GetReportOutput;
import com.tinqinacademy.hotel.api.operations.system.registerguest.RegisterGuestInput;
import com.tinqinacademy.hotel.api.operations.system.registerguest.RegisterGuestOutput;
import com.tinqinacademy.hotel.api.operations.system.updatepartiallyroom.UpdatePartiallyRoomInput;
import com.tinqinacademy.hotel.api.operations.system.updatepartiallyroom.UpdatePartiallyRoomOutput;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomOutput;
import com.tinqinacademy.hotel.api.restroutes.RestApiRoutes;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@FeignClient(name = "hotel")
@Headers("Content-Type: application/json")
public interface HotelRestExportClient {

    @GetMapping(RestApiRoutes.GET_ROOM_INFO)
    GetRoomBasicInfoOutput getRoomBasicInfo(@PathVariable String roomId);

    @GetMapping(RestApiRoutes.CHECK_ROOM_AVAILABILITY)
    CheckAvailableRoomOutput checkAvailableRoom(@RequestParam(required = false) LocalDate startDate,
                                                @RequestParam(required = false) LocalDate endDate,
                                                @RequestParam(required = false) String bedSize,
                                                @RequestParam(required = false) String bathroomType);

    @PostMapping(RestApiRoutes.BOOK_ROOM)
    BookRoomOutput bookRoom(@PathVariable String roomId, @RequestBody BookRoomInput input);

    @DeleteMapping(RestApiRoutes.UNBOOK_ROOM)
    UnbookRoomOutput unbookRoom(@PathVariable String bookingId, @RequestBody UnbookRoomInput input);

    @PatchMapping(RestApiRoutes.UPDATE_PARTIALLY_BOOKING)
    UpdatePartiallyBookingOutput updatePartiallyBooking(@PathVariable String bookingId,
                                                        @RequestBody UpdatePartiallyBookingInput input);

    @GetMapping(RestApiRoutes.GET_BOOKING_HISTORY)
    GetBookingHistoryOutput getBookingHistory(@PathVariable String userId);

    @PostMapping(RestApiRoutes.REGISTER_GUEST)
    RegisterGuestOutput registerGuest(@RequestBody RegisterGuestInput input);

    @GetMapping(RestApiRoutes.GET_REPORT)
    GetReportOutput getReport(@RequestParam(required = false) String startDate,
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
    CreateRoomOutput createRoom(@RequestBody CreateRoomInput input);

    @PutMapping(RestApiRoutes.UPDATE_ROOM)
    UpdateRoomOutput updateRoom(@PathVariable String roomId, @RequestBody UpdateRoomInput input);

    @PatchMapping(RestApiRoutes.UPDATE_PARTIALLY_ROOM)
    UpdatePartiallyRoomOutput updatePartiallyRoom(@PathVariable String roomId, @RequestBody UpdatePartiallyRoomInput input);

    @DeleteMapping(RestApiRoutes.DELETE_ROOM)
    DeleteRoomOutput deleteRoom(@PathVariable("roomId") String id);
}
