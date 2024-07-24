package com.tinqinacademy.hotel.rest.controllers;

import com.tinqinacademy.hotel.core.contracts.HotelService;
import com.tinqinacademy.hotel.persistence.model.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.persistence.model.operations.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.persistence.model.operations.checkavailableroom.CheckAvailableRoomInput;
import com.tinqinacademy.hotel.persistence.model.operations.checkavailableroom.CheckAvailableRoomOutput;
import com.tinqinacademy.hotel.persistence.model.operations.getroombasicinfo.GetRoomBasicInfoInput;
import com.tinqinacademy.hotel.persistence.model.operations.getroombasicinfo.GetRoomBasicInfoOutput;
import com.tinqinacademy.hotel.rest.configurations.RestApiRoutes;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

// @RequestMapping("/hotel")
@RestController
public class HotelController {

    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

//    @Operation(summary = "Check room availability for a certain period",
//            description = "Check room availability for a certain period")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Room is available"),
//            @ApiResponse(responseCode = "404", description = "Room not found")})
//    @GetMapping(RestApiRoutes.CHECK_ROOM_AVAILABILITY) // GET /hotel/rooms
//    public ResponseEntity<?> checkRoomAvailability(@RequestParam LocalDate startDate,
//                                                   @RequestParam LocalDate endDate,
//                                                   @RequestParam Integer bedCount,
//                                                   @RequestParam String bedSize,
//                                                   @RequestParam String bathroomType) {
//        GetRoomInput input = GetRoomInput.builder().startDate(startDate).endDate(endDate).bedCount(bedCount).bedSize(bedSize)
//                // TODO: ? maybe it shouldn't work when i give it wrong enum data
//                //  .bedSize(BedSize.getByCode(bedSize).toString())
//                .bathroomType(bathroomType) // TODO: same here
//                .build();
//
//        GetRoomOutput output = hotelService.getRooms(input);
//
//        return new ResponseEntity<>(output, HttpStatus.OK);
//    }

    @Operation(summary = "TO_BE_REFACTORED - Check room availability for a certain period")
    @GetMapping(RestApiRoutes.CHECK_ROOM_AVAILABILITY) // GET /hotel/rooms
    public ResponseEntity<?> checkAvailableRoom(@RequestParam LocalDate startDate,
                                                @RequestParam LocalDate endDate,
                                                @RequestParam String bedSize,
                                                @RequestParam String bathroomType) {
        CheckAvailableRoomInput input = CheckAvailableRoomInput.builder()
                .startDate(startDate)
                .endDate(endDate)
                .bedSize(bedSize)
                // TODO: ? maybe it shouldn't work when i give it wrong enum data
                //  .bedSize(BedSize.getByCode(bedSize).toString())
                .bathroomType(bathroomType) // TODO: same here
                .build();

        CheckAvailableRoomOutput output = hotelService.checkAvailableRoom(input);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @Operation(summary = "Returns basic info for a room with the specified id")
    @GetMapping(RestApiRoutes.GET_ROOM_INFO)
    public ResponseEntity<?> getRoomBasicInfo(@PathVariable UUID roomId) {

        GetRoomBasicInfoInput input = GetRoomBasicInfoInput.builder()
                .roomId(roomId)
                .build();

        GetRoomBasicInfoOutput output = hotelService.getRoomBasicInfo(input);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @Operation(summary = "Book a room")
    @PostMapping(RestApiRoutes.BOOK_ROOM)
    public ResponseEntity<?> bookRoom(@PathVariable String roomId,
                                      @RequestBody @Valid BookRoomInput input) {

        BookRoomInput updatedInput = input.toBuilder().roomId(roomId).build();

        BookRoomOutput output = hotelService.bookRoom(updatedInput);

        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

    /*
    @Operation(summary = "Get room info by id", description = "Get room info by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room info retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Room not found")})
    @GetMapping(RestApiRoutes.GET_ROOM_INFO) // GET /hotel/{roomId}
    public ResponseEntity<?> getRoomInfo(@PathVariable String roomId) {

        RoomInfoInput input = RoomInfoInput.builder().roomId(roomId).build();

        RoomInfoOutput output = hotelService.getRoomInfo(input);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @Operation(summary = "Book a room", description = "Book a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Room booked successfully"),
            @ApiResponse(responseCode = "400", description = "Room already booked or bad request"),
            @ApiResponse(responseCode = "404", description = "Room not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PostMapping(RestApiRoutes.BOOK_ROOM) // POST /hotel/{roomId}
    public ResponseEntity<?> bookRoom(@PathVariable String roomId,
                                      @RequestBody @Valid BookRoomInput input) {

        BookRoomInput updatedInput = input.toBuilder().roomId(roomId).build();

        BookRoomOutput output = hotelService.bookRoom(updatedInput);

        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

    @Operation(summary = "Unbook a room", description = "Unbook a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Room unbooked successfully"),
            @ApiResponse(responseCode = "404", description = "Room booking not found")})
    @DeleteMapping(RestApiRoutes.UNBOOK_ROOM) // DELETE /hotel/{bookingId}
    public ResponseEntity<?> deleteBooking(@PathVariable String bookingId) {

        DeleteBookingInput input = DeleteBookingInput.builder().bookingId(bookingId).build();

        DeleteBookingOutput output = hotelService.deleteBooking(input);

        return new ResponseEntity<>(output, HttpStatus.NO_CONTENT);
    }
*/

}
