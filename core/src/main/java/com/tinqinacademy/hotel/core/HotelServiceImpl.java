package com.tinqinacademy.hotel.core;

import com.tinqinacademy.hotel.api.error.HotelException;
import com.tinqinacademy.hotel.core.contracts.HotelService;
import com.tinqinacademy.hotel.persistence.model.Booking;
import com.tinqinacademy.hotel.persistence.model.Guest;
import com.tinqinacademy.hotel.persistence.model.Room;
import com.tinqinacademy.hotel.persistence.model.User;
import com.tinqinacademy.hotel.persistence.model.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.model.enums.BedSize;
import com.tinqinacademy.hotel.persistence.model.operations.hotel.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.persistence.model.operations.hotel.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.persistence.model.operations.hotel.checkavailableroom.CheckAvailableRoomInput;
import com.tinqinacademy.hotel.persistence.model.operations.hotel.checkavailableroom.CheckAvailableRoomOutput;
import com.tinqinacademy.hotel.persistence.model.operations.hotel.getroombasicinfo.GetRoomBasicInfoInput;
import com.tinqinacademy.hotel.persistence.model.operations.hotel.getroombasicinfo.GetRoomBasicInfoOutput;
import com.tinqinacademy.hotel.persistence.model.operations.hotel.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.persistence.model.operations.hotel.unbookroom.UnbookRoomOutput;
import com.tinqinacademy.hotel.persistence.model.operations.hotel.updatepartiallybooking.UpdatePartiallyBookingInput;
import com.tinqinacademy.hotel.persistence.model.operations.hotel.updatepartiallybooking.UpdatePartiallyBookingOutput;
import com.tinqinacademy.hotel.persistence.model.operations.hotel.updatepartiallybooking.UpdatePartiallyGuestInput;
import com.tinqinacademy.hotel.persistence.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
            log.error("Invalid bed size or bathroom type provided: bedSize={}, bathroomType={}", input.getBedSize(), input.getBathroomType());
            throw new HotelException("Invalid bed size or bathroom type.");
        }

        if (input.getStartDate().isAfter(input.getEndDate())) {
            log.error("Start date should be before end date");
            throw new HotelException("Start date should be before end date.");
        }

        List<Room> availableRooms = roomRepository.findAvailableRooms(input.getStartDate(), input.getEndDate());
        List<Room> roomsMatchingCriteria = roomRepository.findRoomsByBedSizeAndBathroomType(bedSize, bathroomType);
        List<String> availableRoomIds = new ArrayList<>();

        for (Room room : availableRooms) {
            if (roomsMatchingCriteria.contains(room)) {
                availableRoomIds.add(room.getId().toString());
            }
        }

        CheckAvailableRoomOutput output = CheckAvailableRoomOutput.builder()
                .ids(availableRoomIds)
                .build();

        log.info("Ended checkAvailableRoom with output: {}", output);
        return output;
    }

    @Override
    public GetRoomBasicInfoOutput getRoomBasicInfo(GetRoomBasicInfoInput input) {
        log.info("Started getRoomBasicInfo with input: {}", input);

        UUID roomId = UUID.fromString(input.getRoomId());
        Room room = roomRepository.findByIdWithBeds(roomId).orElseThrow(() -> new HotelException("Room not found"));

        List<Booking> bookings = bookingRepository.findAllByRoomId(room.getId()).orElse(new ArrayList<>());

        List<LocalDate> datesOccupied = new ArrayList<>();
        for (Booking booking : bookings) {
            LocalDate startDate = booking.getStartDate();
            LocalDate endDate = booking.getEndDate();
            while (startDate.isBefore(endDate)) {
                datesOccupied.add(startDate);
                startDate = startDate.plusDays(1);
            }
        }

        GetRoomBasicInfoOutput output = conversionService.convert(room, GetRoomBasicInfoOutput.class);
        output.setDatesOccupied(datesOccupied);

//        GetRoomBasicInfoOutput output = GetRoomBasicInfoOutput.builder()
//                .id(room.getId())
//                .price(room.getPrice())
//                .floor(room.getFloor())
//                .bedSize(room.getBeds().stream().findFirst().map(Bed::getBedSize).orElse(null))
//                .bathroomType(room.getBathroomType())
//                .datesOccupied(datesOccupied)
//                .roomNumber(room.getRoomNumber())
//                .build();

        log.info("Ended getRoomBasicInfo with output: {}", output);
        return output;
    }

    @Override
    public BookRoomOutput bookRoom(BookRoomInput input) {
        log.info("Started bookRoom with input: {}", input);

        UUID roomId = UUID.fromString(input.getRoomId());
        LocalDate startDate = input.getStartDate();
        LocalDate endDate = input.getEndDate();

        Room room = roomRepository.findById(roomId).orElseThrow(() -> new HotelException("room not found"));

        List<Room> availableRooms = roomRepository.findAvailableRooms(startDate, endDate);

        if (!availableRooms.contains(room)) {
            throw new RuntimeException("Room is not available for the selected dates");
        }

        User user = userRepository
                .findByPhoneNumberAndFirstNameAndLastName(input.getPhoneNo(), input.getFirstName(), input.getLastName())
                .orElseThrow(() -> new HotelException("no user found"));

        BigDecimal totalPrice = room.getPrice().add(BigDecimal.valueOf(50)); // Example price; should add logic

        Booking booking = Booking.builder()
                .room(room)
                .user(user)
                .startDate(startDate)
                .endDate(endDate)
                .totalPrice(totalPrice)
                .guests(Set.of()) // Empty set, later will have endpoint for adding guests for certain booking
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
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new HotelException("booking not found"));
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

        Booking booking = bookingRepository.findById(UUID.fromString(input.getId()))
                .orElseThrow(() -> new HotelException("booking not found"));

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
                throw new HotelException("room already exists");
            }

            // logic: shouldn't be able to create a new room

            booking.getRoom().setRoomNumber(input.getRoomNumber());
        }

        if (input.getGuests() != null && !input.getGuests().isEmpty()) { // if guests field is not empty

            for (UpdatePartiallyGuestInput guest : input.getGuests()) { // add each of the filled guests

                Guest currentGuest = Guest.builder()
                        .firstName(guest.getFirstName())
                        .lastName(guest.getLastName())
                        .phoneNumber(guest.getPhoneNumber())
                        .idCardNumber(guest.getIdCardNumber())
                        .idCardValidity(guest.getIdCardValidity())
                        .idCardIssueDate(guest.getIdCardIssueDate())
                        .idCardIssueAuthority(guest.getIdCardIssueAuthority())
                        .birthdate(guest.getBirthdate())
                        .build();

                guestRepository.save(currentGuest);

                booking.getGuests().add(currentGuest);
            }
        }

        bookingRepository.save(booking);

        UpdatePartiallyBookingOutput output = UpdatePartiallyBookingOutput.builder()
                .id(booking.getId())
                .build();

        log.info("Ended updatePartiallyBooking with output: {}", output);
        return output;
    }

}