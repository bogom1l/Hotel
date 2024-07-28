package com.tinqinacademy.hotel.core;

import com.tinqinacademy.hotel.api.error.HotelException;
import com.tinqinacademy.hotel.core.contracts.SystemService;
import com.tinqinacademy.hotel.persistence.model.Bed;
import com.tinqinacademy.hotel.persistence.model.Booking;
import com.tinqinacademy.hotel.persistence.model.Guest;
import com.tinqinacademy.hotel.persistence.model.Room;
import com.tinqinacademy.hotel.persistence.model.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.model.enums.BedSize;
import com.tinqinacademy.hotel.persistence.model.operations.system.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.persistence.model.operations.system.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.persistence.model.operations.system.getreport.GetReportInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.getreport.GetReportOutput;
import com.tinqinacademy.hotel.persistence.model.operations.system.getreport.GuestOutput;
import com.tinqinacademy.hotel.persistence.model.operations.system.registerguest.GuestInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.registerguest.RegisterGuestInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.registerguest.RegisterGuestOutput;
import com.tinqinacademy.hotel.persistence.model.operations.system.updatepartiallyroom.UpdatePartiallyRoomInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.updatepartiallyroom.UpdatePartiallyRoomOutput;
import com.tinqinacademy.hotel.persistence.model.operations.system.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.updateroom.UpdateRoomOutput;
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

        for (GuestInput guestInput : input.getGuests()) {
            Room room = roomRepository.findById(UUID.fromString(guestInput.getRoomId()))
                    .orElseThrow(() -> new HotelException("no room found"));

//            Guest guest = Guest.builder()
//                    .firstName(guestInput.getFirstName())
//                    .lastName(guestInput.getLastName())
//                    .phoneNumber(guestInput.getPhoneNumber())
//                    .idCardNumber(guestInput.getIdCardNumber())
//                    .idCardValidity(guestInput.getIdCardValidity())
//                    .idCardIssueAuthority(guestInput.getIdCardIssueAuthority())
//                    .idCardIssueDate(guestInput.getIdCardIssueDate())
//                    .birthdate(guestInput.getBirthdate())
//                    .build();

            Guest guest = conversionService.convert(guestInput, Guest.class);

            Booking booking = bookingRepository.findByRoomIdAndStartDateAndEndDate(
                            room.getId(), guestInput.getStartDate(), guestInput.getEndDate())
                    .orElseThrow(() -> new HotelException("no booking found"));

            booking.getGuests().add(guest);

            guestRepository.save(guest);
            bookingRepository.save(booking);
        }

        RegisterGuestOutput output = RegisterGuestOutput.builder().build();
        log.info("Ended registerGuest with output: {}", output);
        return output;
    }


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
                    GuestOutput guestOutput = convertGuestToGuestOutput(guest, booking);
                    guestMap.putIfAbsent(guest.getId(), guestOutput);
                }
            }
        }

        //2. Search by guest details
        if (input.getFirstName() != null &&
                input.getLastName() != null &&
                input.getPhoneNo() != null &&
                input.getIdCardNo() != null &&
                input.getIdCardValidity() != null &&
                input.getIdCardIssueAuthority() != null &&
                input.getIdCardIssueDate() != null) {

            List<Guest> matchingGuests = guestRepository.findMatchingGuests(
                    input.getFirstName(),
                    input.getLastName(),
                    input.getPhoneNo(),
                    input.getIdCardNo(),
                    input.getIdCardValidity(),
                    input.getIdCardIssueAuthority(),
                    input.getIdCardIssueDate()
            ).orElse(Collections.emptyList());

            for (Guest guest : matchingGuests) {
                List<Booking> bookings = bookingRepository.findByGuestIdCardNumber(guest.getIdCardNumber())
                        .orElse(Collections.emptyList());

                if (bookings.isEmpty()) {
                    if (!guestMap.containsKey(guest.getId())) {
                        GuestOutput guestOutput = convertGuestToGuestOutput(guest, null);
                        guestMap.put(guest.getId(), guestOutput);
                    }
                } else {
                    for (Booking booking : bookings) {
                        if (!guestMap.containsKey(guest.getId())) {
                            GuestOutput guestOutput = convertGuestToGuestOutput(guest, booking);
                            guestMap.put(guest.getId(), guestOutput);
                        }
                    }
                }

            }
        }

        //3. Search by room number
        if (input.getRoomNo() != null) {
            Room room = roomRepository.findByRoomNumber(input.getRoomNo())
                    .orElseThrow(() -> new HotelException("No room found with number: " + input.getRoomNo()));

            List<Booking> bookings = bookingRepository.findByRoomId(room.getId())
                    .orElse(Collections.emptyList());

            for (Booking booking : bookings) {
                for (Guest guest : booking.getGuests()) {
                    if (!guestMap.containsKey(guest.getId())) {
                        GuestOutput guestOutput = convertGuestToGuestOutput(guest, booking);
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

    private GuestOutput convertGuestToGuestOutput(Guest guest, Booking booking) {
        return GuestOutput.builder()
                .startDate(booking != null ? booking.getStartDate() : null)
                .endDate(booking != null ? booking.getEndDate() : null)
                .firstName(guest.getFirstName())
                .lastName(guest.getLastName())
                .phoneNo(guest.getPhoneNumber())
                .idCardNo(guest.getIdCardNumber())
                .idCardValidity(guest.getIdCardValidity())
                .idCardIssueAuthority(guest.getIdCardIssueAuthority())
                .idCardIssueDate(guest.getIdCardIssueDate())
                .build();
    }


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

//        Room room = Room.builder()
//                .beds(List.of(bed))
//                .bathroomType(BathroomType.getByCode(input.getBathroomType()))
//                .floor(input.getFloor())
//                .roomNumber(input.getRoomNumber())
//                .price(input.getPrice())
//                .build();

        Room room = conversionService.convert(input, Room.class);
        room.setBeds(List.of(bed));

        bedRepository.save(bed);
        roomRepository.save(room);

        CreateRoomOutput output = CreateRoomOutput.builder()
                .id(room.getId())
                .build();

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

        if (roomRepository.existsByRoomNumber(input.getRoomNo())) {
            throw new HotelException("Room number already exists");
        }

        if (input.getBathroomType() == null ||
                input.getBedSize() == null ||
                input.getRoomNo() == null ||
                input.getPrice() == null) {
            throw new HotelException("Please fill all the fields.");
        }

        Room room = roomRepository.findById(UUID.fromString(input.getRoomId()))
                .orElseThrow(() -> new HotelException("No room found with id: " + input.getRoomId()));

        room.setBathroomType(BathroomType.getByCode(input.getBathroomType()));
        room.setRoomNumber(input.getRoomNo());
        room.setPrice(input.getPrice());

        List<Bed> bedsInCurrentRoom = room.getBeds();

        for (Bed bed : bedsInCurrentRoom) {
            bed.setBedSize(BedSize.getByCode(input.getBedSize()));
        }

        roomRepository.save(room);

        UpdateRoomOutput output = UpdateRoomOutput.builder()
                .id(room.getId())
                .build();

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

        if (input.getRoomNo() != null) {
            if (roomRepository.existsByRoomNumber(input.getRoomNo())) {
                throw new HotelException("Room number already exists");
            }
            room.setRoomNumber(input.getRoomNo());
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

        UpdatePartiallyRoomOutput output = UpdatePartiallyRoomOutput.builder()
                .id(room.getId())
                .build();

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

}