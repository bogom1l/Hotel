package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.error.HotelException;
import com.tinqinacademy.hotel.core.services.contracts.HotelService;
import com.tinqinacademy.hotel.persistence.model.Booking;
import com.tinqinacademy.hotel.persistence.model.Guest;
import com.tinqinacademy.hotel.persistence.model.Room;
import com.tinqinacademy.hotel.persistence.model.User;
import com.tinqinacademy.hotel.persistence.model.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.model.enums.BedSize;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomOutput;
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
    public CheckAvailableRoomOutput checkAvailableRoom(CheckAvailableRoomInput input) {
        log.info("Started checkAvailableRoom with input: {}", input);

        BedSize bedSize = BedSize.getByCode(input.getBedSize());
        BathroomType bathroomType = BathroomType.getByCode(input.getBathroomType());

        if (bedSize == BedSize.UNKNOWN || bathroomType == BathroomType.UNKNOWN) {
            throw new HotelException("Invalid bed size or bathroom type.");
        }

        if (input.getStartDate().isAfter(input.getEndDate())) {
            throw new HotelException("Start date should be before end date.");
        }

        List<Room> availableRoomsBetweenDates = roomRepository.findAvailableRoomsBetweenDates(input.getStartDate(), input.getEndDate()).orElseThrow(() -> new HotelException("No available rooms found"));

        List<Room> roomsMatchingCriteria = roomRepository.findRoomsByBedSizeAndBathroomType(bedSize, bathroomType);

        List<String> availableRoomIds = availableRoomsBetweenDates.stream().filter(roomsMatchingCriteria::contains).map(room -> room.getId().toString()).toList();

        CheckAvailableRoomOutput output = conversionService.convert(availableRoomIds, CheckAvailableRoomOutput.class);

        log.info("Ended checkAvailableRoom with output: {}", output);
        return output;
    }

    @Override
    public GetRoomBasicInfoOutput getRoomBasicInfo(GetRoomBasicInfoInput input) {
        log.info("Started getRoomBasicInfo with input: {}", input);

        UUID roomId = UUID.fromString(input.getRoomId());
        Room room = roomRepository.findByIdWithBeds(roomId).orElseThrow(() -> new HotelException("Room not found"));

        List<Booking> bookings = bookingRepository.findAllByRoomId(room.getId()).orElse(new ArrayList<>());

        List<LocalDate> datesOccupied = bookings.stream().flatMap(booking -> booking.getStartDate().datesUntil(booking.getEndDate())).toList();

        GetRoomBasicInfoOutput output = conversionService.convert(room, GetRoomBasicInfoOutput.GetRoomBasicInfoOutputBuilder.class).datesOccupied(datesOccupied).build();

        log.info("Ended getRoomBasicInfo with output: {}", output);
        return output;
    }

    @Override
    public BookRoomOutput bookRoom(BookRoomInput input) {
        log.info("Started bookRoom with input: {}", input);

        UUID roomId = UUID.fromString(input.getRoomId());
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new HotelException("Room not found"));

        if (!roomRepository.isRoomAvailableByRoomIdAndBetweenDates(room.getId(), input.getStartDate(), input.getEndDate())) {
            throw new HotelException("Room is not available for the selected dates: " + input.getStartDate() + " - " + input.getEndDate());
        }

        // todo: logic: find user or create user?
        User user = userRepository.findByPhoneNumberAndFirstNameAndLastName(input.getPhoneNumber(), input.getFirstName(), input.getLastName()).orElseThrow(() -> new HotelException(String.format("No user found with first name: %s, last name: %s, phone number: %s", input.getFirstName(), input.getLastName(), input.getPhoneNumber())));

        Booking booking = Booking.builder().room(room).user(user).startDate(input.getStartDate()).endDate(input.getEndDate()).totalPrice(room.getPrice()).guests(Set.of()) // Empty set, later will have endpoint for adding guests for certain booking
                .build();

        bookingRepository.save(booking);

        BookRoomOutput output = BookRoomOutput.builder().build();
        log.info("Ended bookRoom with output: {}", output);
        return output;
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

    @Override
    public GetBookingHistoryOutput getBookingHistory(GetBookingHistoryInput input) {
        log.info("Started getBookingHistory with input: {}", input);

        User user = userRepository.findByPhoneNumber(input.getPhoneNumber()).orElseThrow(() -> new HotelException("No user found with phone number: " + input.getPhoneNumber()));

        List<Booking> bookings = bookingRepository.findAllByUserId(user.getId()).orElseThrow(() -> new HotelException("No bookings found for user with phone number: " + input.getPhoneNumber()));

        List<GetBookingHistoryBookingOutput> bookingsOutput = bookings.stream().map(booking -> conversionService.convert(booking, GetBookingHistoryBookingOutput.class)).toList();

        GetBookingHistoryOutput output = GetBookingHistoryOutput.builder().bookings(bookingsOutput).build();

        log.info("Ended getBookingHistory with output: {}", output);
        return output;
    }

}