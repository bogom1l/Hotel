package com.tinqinacademy.hotel.core;

import com.tinqinacademy.hotel.api.error.HotelException;
import com.tinqinacademy.hotel.core.contracts.SystemService;
import com.tinqinacademy.hotel.persistence.model.*;
import com.tinqinacademy.hotel.persistence.model.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.model.enums.BedSize;
import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.api.operations.system.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.operations.system.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.operations.system.getallusers.GetAllUsersInput;
import com.tinqinacademy.hotel.api.operations.system.getallusers.GetAllUsersOutput;
import com.tinqinacademy.hotel.api.operations.system.getallusers.UserOutput;
import com.tinqinacademy.hotel.api.operations.system.getreport.GetReportInput;
import com.tinqinacademy.hotel.api.operations.system.getreport.GetReportOutput;
import com.tinqinacademy.hotel.api.operations.system.getreport.GuestOutput;
import com.tinqinacademy.hotel.api.operations.system.registerguest.GuestInput;
import com.tinqinacademy.hotel.api.operations.system.registerguest.RegisterGuestInput;
import com.tinqinacademy.hotel.api.operations.system.registerguest.RegisterGuestOutput;
import com.tinqinacademy.hotel.api.operations.system.updatepartiallyroom.UpdatePartiallyRoomInput;
import com.tinqinacademy.hotel.api.operations.system.updatepartiallyroom.UpdatePartiallyRoomOutput;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomOutput;
import com.tinqinacademy.hotel.persistence.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class SystemServiceImpl implements SystemService {

    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final BedRepository bedRepository;
    private final ConversionService conversionService;

    public SystemServiceImpl(RoomRepository roomRepository, GuestRepository guestRepository, BookingRepository bookingRepository, UserRepository userRepository, BedRepository bedRepository, ConversionService conversionService) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.bedRepository = bedRepository;
        this.conversionService = conversionService;
    }

    @Override
    public RegisterGuestOutput registerGuest(RegisterGuestInput input) {
        log.info("Started registerGuest with input: {}", input);

        // todo: logic: should room be in the List, or should it be a separate field?

        for (GuestInput guestInput : input.getGuests()) {
            Room room = roomRepository.findById(UUID.fromString(guestInput.getRoomId()))
                    .orElseThrow(() -> new HotelException("No room found"));

            Guest guest = conversionService.convert(guestInput, Guest.class);

            Booking booking = bookingRepository.findByRoomIdAndStartDateAndEndDate(
                            room.getId(), guestInput.getStartDate(), guestInput.getEndDate())
                    .orElseThrow(() -> new HotelException("No booking found"));

            booking.getGuests().add(guest);

            guestRepository.save(guest);
            bookingRepository.save(booking);
        }

        RegisterGuestOutput output = RegisterGuestOutput.builder().build();
        log.info("Ended registerGuest with output: {}", output);
        return output;
    }


    // todo: make this method more readable (split into smaller methods)
    @Override
    public GetReportOutput getReport(GetReportInput input) { // optional fields - fill only 1) or 2) or 3)
        log.info("Started getRoomReport with input: {}", input);

        Map<UUID, GuestOutput> guestMap = new HashMap<>();

        //1. Search by startDate and endDate
        if (input.getStartDate() != null && input.getEndDate() != null) {

            List<Booking> bookings = bookingRepository
                    .findByDateRange(LocalDate.parse(input.getStartDate()), LocalDate.parse(input.getEndDate()))
                    .orElse(Collections.emptyList());

            for (Booking booking : bookings) {
                for (Guest guest : booking.getGuests()) {
                    GuestOutput guestOutput =
                            conversionService.convert(guest, GuestOutput.GuestOutputBuilder.class)
                                    .startDate(booking.getStartDate())
                                    .endDate(booking.getEndDate())
                                    .build();
                    guestMap.putIfAbsent(guest.getId(), guestOutput);
                }
            }
        }

        //2. Search by guest details
        if (input.getFirstName() != null &&
                input.getLastName() != null &&
                input.getPhoneNumber() != null &&
                input.getIdCardNumber() != null &&
                input.getIdCardValidity() != null &&
                input.getIdCardIssueAuthority() != null &&
                input.getIdCardIssueDate() != null) {

            List<Guest> matchingGuests = guestRepository.findMatchingGuests(
                    input.getFirstName(),
                    input.getLastName(),
                    input.getPhoneNumber(),
                    input.getIdCardNumber(),
                    input.getIdCardValidity(),
                    input.getIdCardIssueAuthority(),
                    input.getIdCardIssueDate()
            ).orElse(Collections.emptyList());

            for (Guest guest : matchingGuests) {
                List<Booking> bookings = bookingRepository.findByGuestIdCardNumber(guest.getIdCardNumber())
                        .orElse(Collections.emptyList());

                if (bookings.isEmpty()) {
                    if (!guestMap.containsKey(guest.getId())) {
                        GuestOutput guestOutput = conversionService.convert(guest, GuestOutput.class);
                        guestMap.put(guest.getId(), guestOutput);
                    }
                } else {
                    for (Booking booking : bookings) {
                        if (!guestMap.containsKey(guest.getId())) {
                            GuestOutput guestOutput =
                                    conversionService.convert(guest, GuestOutput.GuestOutputBuilder.class)
                                            .startDate(booking.getStartDate())
                                            .endDate(booking.getEndDate())
                                            .build();
                            guestMap.put(guest.getId(), guestOutput);
                        }
                    }
                }

            }
        }

        //3. Search by room number
        if (input.getRoomNumber() != null) {
            Room room = roomRepository.findByRoomNumber(input.getRoomNumber())
                    .orElseThrow(() -> new HotelException("No room found with number: " + input.getRoomNumber()));

            List<Booking> bookings = bookingRepository.findByRoomId(room.getId())
                    .orElse(Collections.emptyList());

            for (Booking booking : bookings) {
                for (Guest guest : booking.getGuests()) {
                    if (!guestMap.containsKey(guest.getId())) {
                        GuestOutput guestOutput =
                                conversionService.convert(guest, GuestOutput.GuestOutputBuilder.class)
                                        .startDate(booking.getStartDate())
                                        .endDate(booking.getEndDate())
                                        .build();
                        guestMap.put(guest.getId(), guestOutput);
                    }
                }
            }
        }

        if (guestMap.isEmpty()) {
            throw new HotelException("No matching guests found for the provided criteria.");
        }

        List<GuestOutput> values = new ArrayList<>(guestMap.values());
        GetReportOutput output = GetReportOutput.builder()
                .guests(values)
                .build();
        log.info("Ended getRoomReport with output: {}", output);
        return output;
    }

    // todo: logic: create the bed or find the bed?
    @Override
    public CreateRoomOutput createRoom(CreateRoomInput input) {
        log.info("Started createRoom with input: {}", input);

        if (BedSize.getByCode(input.getBedSize()).equals(BedSize.UNKNOWN)) {
            throw new HotelException("No bed size found");
        }
        if (BathroomType.getByCode(input.getBathroomType()).equals(BathroomType.UNKNOWN)) {
            throw new HotelException("No bathroom type found");
        }

        Bed bed = Bed.builder()
                .bedSize(BedSize.getByCode(input.getBedSize()))
                .capacity(BedSize.getByCode(input.getBedSize()).getCapacity())
                .build();

        Room room = conversionService
                .convert(input, Room.RoomBuilder.class)
                .beds(List.of(bed))
                .build();

        bedRepository.save(bed);
        roomRepository.save(room);

        CreateRoomOutput output = conversionService.convert(room, CreateRoomOutput.class);

        log.info("Ended createRoom with output: {}", output);
        return output;
    }

    @Override
    public UpdateRoomOutput updateRoom(UpdateRoomInput input) {
        log.info("Started updateRoom with input: {}", input);

        if (BedSize.getByCode(input.getBedSize()).equals(BedSize.UNKNOWN)) {
            throw new HotelException("No bed size found");
        }

        if (BathroomType.getByCode(input.getBathroomType()).equals(BathroomType.UNKNOWN)) {
            throw new HotelException("No bathroom type found");
        }

        if (roomRepository.existsByRoomNumber(input.getRoomNumber())) {
            throw new HotelException("Room number already exists");
        }

        if (input.getBathroomType() == null ||
                input.getBedSize() == null ||
                input.getRoomNumber() == null ||
                input.getPrice() == null) {
            throw new HotelException("Please fill all the fields.");
        }

        Room room = roomRepository.findById(UUID.fromString(input.getRoomId()))
                .orElseThrow(() -> new HotelException("No room found with id: " + input.getRoomId()));

        room.setBathroomType(BathroomType.getByCode(input.getBathroomType()));
        room.setRoomNumber(input.getRoomNumber());
        room.setPrice(input.getPrice());

        List<Bed> bedsInCurrentRoom = room.getBeds();

        for (Bed bed : bedsInCurrentRoom) {
            bed.setBedSize(BedSize.getByCode(input.getBedSize()));
        }

        roomRepository.save(room);

        UpdateRoomOutput output = conversionService.convert(room, UpdateRoomOutput.class);

        log.info("Ended updateRoom with output: {}", output);
        return output;
    }


    @Override
    public UpdatePartiallyRoomOutput updatePartiallyRoom(UpdatePartiallyRoomInput input) {
        log.info("Started updatePartiallyRoom with input: {}", input);

        Room room = roomRepository.findById(UUID.fromString(input.getRoomId()))
                .orElseThrow(() -> new HotelException("No room found with id: " + input.getRoomId()));

        if (input.getBathroomType() != null) {
            if (BathroomType.getByCode(input.getBathroomType()).equals(BathroomType.UNKNOWN)) {
                throw new HotelException("No bathroom type found");
            }

            room.setBathroomType(BathroomType.getByCode(input.getBathroomType()));
        }

        if (input.getRoomNumber() != null) {
            if (roomRepository.existsByRoomNumber(input.getRoomNumber())) {
                throw new HotelException("Room number already exists");
            }
            room.setRoomNumber(input.getRoomNumber());
        }

        if (input.getPrice() != null) {
            room.setPrice(input.getPrice());
        }

        if (input.getBedSize() != null) {
            if (BedSize.getByCode(input.getBedSize()).equals(BedSize.UNKNOWN)) {
                throw new HotelException("No bed size found");
            }

            List<Bed> bedsInCurrentRoom = room.getBeds();

            for (Bed bed : bedsInCurrentRoom) {
                bed.setBedSize(BedSize.getByCode(input.getBedSize()));
            }
        }

        roomRepository.save(room);

        UpdatePartiallyRoomOutput output = conversionService.convert(room, UpdatePartiallyRoomOutput.class);

        log.info("Ended updatePartiallyRoom with output: {}", output);
        return output;
    }

    @Override
    public DeleteRoomOutput deleteRoom(DeleteRoomInput input) {
        log.info("Started deleteRoom with input: {}", input);

        Room room = roomRepository.findById(UUID.fromString(input.getId()))
                .orElseThrow(() -> new HotelException("No room found with id: " + input.getId()));

        roomRepository.delete(room);

        DeleteRoomOutput output = DeleteRoomOutput.builder().build();
        log.info("Ended deleteRoom with output: {}", output);
        return output;
    }

    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    @Override
    public void deleteAllGuests() {
        guestRepository.deleteAll();
    }

    @Override
    public void deleteAllBookings() {
        bookingRepository.deleteAll();
    }

    @Override
    public GetAllUsersOutput getAllUsersByPartialName(GetAllUsersInput input) {
        log.info("Started getAllUsers with input: {}", input);

        List<User> users = userRepository.findUsersByPartialName(input.getPartialName());

        List<UserOutput> usersOutput = users
                .stream()
                .map(user -> conversionService.convert(user, UserOutput.class))
                .toList();

        GetAllUsersOutput output = GetAllUsersOutput.builder()
                .users(usersOutput)
                .count(users.size())
                .build();

        log.info("Ended getAllUsers with output: {}", output);
        return output;
    }

}