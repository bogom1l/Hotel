package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.exceptions.HotelException;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.core.services.contracts.HotelService;
import com.tinqinacademy.hotel.persistence.model.Booking;
import com.tinqinacademy.hotel.persistence.model.Guest;
import com.tinqinacademy.hotel.persistence.model.Room;
import com.tinqinacademy.hotel.persistence.model.User;
import com.tinqinacademy.hotel.persistence.model.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.model.enums.BedSize;
import com.tinqinacademy.hotel.api.operations.hotel.checkavailableroom.CheckAvailableRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.checkavailableroom.CheckAvailableRoomOutput;
import com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory.GetBookingHistoryBookingOutput;
import com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory.GetBookingHistoryInput;
import com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory.GetBookingHistoryOutput;
import com.tinqinacademy.hotel.api.operations.hotel.getroombasicinfo.GetRoomBasicInfoInput;
import com.tinqinacademy.hotel.api.operations.hotel.getroombasicinfo.GetRoomBasicInfoOutput;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomOutput;
import com.tinqinacademy.hotel.api.operations.hotel.updatepartiallybooking.UpdatePartiallyBookingInput;
import com.tinqinacademy.hotel.api.operations.hotel.updatepartiallybooking.UpdatePartiallyBookingOutput;
import com.tinqinacademy.hotel.api.operations.hotel.updatepartiallybooking.UpdatePartiallyGuestInput;
import com.tinqinacademy.hotel.persistence.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
public class HotelServiceImpl implements HotelService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final BedRepository bedRepository;
    private final GuestRepository guestRepository;
    private final ConversionService conversionService;

    public HotelServiceImpl(RoomRepository roomRepository, UserRepository userRepository, BookingRepository bookingRepository, BedRepository bedRepository, GuestRepository guestRepository, ConversionService conversionService) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.bedRepository = bedRepository;
        this.guestRepository = guestRepository;
        this.conversionService = conversionService;
    }


    @Override
    public UnbookRoomOutput unbookRoom(UnbookRoomInput input) {
        log.info("Started unbookRoom with input: {}", input);

        UUID bookingId = UUID.fromString(input.getBookingId());
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new HotelException("Booking not found"));

        bookingRepository.delete(booking);

        UnbookRoomOutput output = UnbookRoomOutput.builder().build();

        log.info("Ended unbookRoom with output: {}", output);
        return output;
    }

    @Override
    public void deleteAllRooms() {
        log.info("Started deleteAllRooms");

        roomRepository.deleteAll();

        log.info("Ended deleteAllRooms successfully");
    }

    @Override
    public void deleteAllBeds() {
        log.info("Started deleteAllBeds");

        bedRepository.deleteAll();

        log.info("Ended deleteAllBeds successfully");
    }

    @Override
    public UpdatePartiallyBookingOutput updatePartiallyBooking(UpdatePartiallyBookingInput input) {
        log.info("Started updatePartiallyBooking with input: {}", input);

        Booking booking = bookingRepository.findById(UUID.fromString(input.getBookingId())).orElseThrow(() -> new HotelException("Booking not found"));

        if (input.getStartDate() != null) {
            booking.setStartDate(LocalDate.parse(input.getStartDate()));
        }

        if (input.getEndDate() != null) {
            booking.setEndDate(LocalDate.parse(input.getEndDate()));
        }

        if (input.getTotalPrice() != null) {
            booking.setTotalPrice(input.getTotalPrice());
        }

        if (input.getRoomNumber() != null) {
            if (roomRepository.existsByRoomNumber(input.getRoomNumber())) {
                throw new HotelException("Room number already exists. Please choose different room number.");
            }

            booking.getRoom().setRoomNumber(input.getRoomNumber()); // logic: shouldn't be able to create a new room
        }

        if (input.getGuests() != null && !input.getGuests().isEmpty()) { // if guests field is not left empty

            for (UpdatePartiallyGuestInput guest : input.getGuests()) { // add each of the filled guests

                Guest newGuest = conversionService.convert(guest, Guest.class);

                guestRepository.save(newGuest);

                booking.getGuests().add(newGuest);
            }
        }

        bookingRepository.save(booking);

        UpdatePartiallyBookingOutput output = conversionService.convert(booking, UpdatePartiallyBookingOutput.class);

        log.info("Ended updatePartiallyBooking with output: {}", output);
        return output;
    }


}