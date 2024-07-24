package com.tinqinacademy.hotel.core;

import com.tinqinacademy.hotel.api.error.HotelException;
import com.tinqinacademy.hotel.core.contracts.HotelService;
import com.tinqinacademy.hotel.persistence.model.Bed;
import com.tinqinacademy.hotel.persistence.model.Booking;
import com.tinqinacademy.hotel.persistence.model.Room;
import com.tinqinacademy.hotel.persistence.model.operations.checkavailableroom.CheckAvailableRoomInput;
import com.tinqinacademy.hotel.persistence.model.operations.checkavailableroom.CheckAvailableRoomOutput;
import com.tinqinacademy.hotel.persistence.model.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.model.enums.BedSize;
import com.tinqinacademy.hotel.persistence.model.operations.getroombasicinfo.GetRoomBasicInfoInput;
import com.tinqinacademy.hotel.persistence.model.operations.getroombasicinfo.GetRoomBasicInfoOutput;
import com.tinqinacademy.hotel.persistence.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HotelServiceImpl implements HotelService {

    private final BedRepository bedRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final GuestRepository guestRepository;
    private final BookingRepository bookingRepository;

    public HotelServiceImpl(BedRepository bedRepository, RoomRepository roomRepository, UserRepository userRepository, GuestRepository guestRepository, BookingRepository bookingRepository) {
        this.bedRepository = bedRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.guestRepository = guestRepository;
        this.bookingRepository = bookingRepository;
    }

    /*
        @Override
        public GetRoomOutput getRooms(GetRoomInput input) {
            log.info("Start getRooms with input: {}", input);

            GetRoomOutput output = GetRoomOutput.builder()
                    .ids(List.of("1", "2", "3", "63"))
                    .build();

            log.info("End getRooms with output: {}", output);
            return output;
        }

        @Override
        public RoomInfoOutput getRoomInfo(RoomInfoInput input) {
            log.info("Start getRoomInfo with input: {}", input);

            Random random = new Random();
            List<LocalDate> sampleDates = generateSampleDates();

            RoomInfoOutput output = RoomInfoOutput.builder() // sample random data
                    .id(input.getRoomId())
                    .price(BigDecimal.valueOf(random.nextDouble(50_000) + 1))
                    .floor(random.nextInt(10) + 1)
                    .bedSize(BedSize.DOUBLE)
                    .bathroomType(BathroomType.PRIVATE)
                    .bedCount(random.nextInt(5) + 1)
                    .datesOccupied(sampleDates)
                    .build();

            log.info("End getRoomInfo with output: {}", output);
            return output;
        }

        private List<LocalDate> generateSampleDates() {
            List<LocalDate> sampleDates = new ArrayList<>();
            sampleDates.add(LocalDate.now());
            sampleDates.add(LocalDate.now().plusDays(1));
            sampleDates.add(LocalDate.now().plusDays(2));
            sampleDates.add(LocalDate.now().plusDays(3));

            return sampleDates;
        }

        @Override
        public BookRoomOutput bookRoom(BookRoomInput input) {
            log.info("Start bookRoom with input: {}", input);

            BookRoomOutput output = BookRoomOutput.builder().build();

            log.info("End bookRoom with output: {}", output);
            return output;
        }

        @Override
        public DeleteBookingOutput deleteBooking(DeleteBookingInput input) {
            log.info("Start deleteBooking with input: {}", input);

            DeleteBookingOutput output = DeleteBookingOutput.builder().build();

            log.info("End deleteBooking with output: {}", output);
            return output;
        }


     */
    @Override
    public CheckAvailableRoomOutput checkAvailableRoom(CheckAvailableRoomInput input) {
        log.info("Start checkAvailableRoom with input: {}", input);

        //TODO maybe needs refactoring

        BedSize bedSize;
        BathroomType bathroomType;

        // Convert string inputs to enum
        try {
            bedSize = BedSize.getByCode(input.getBedSize());
            bathroomType = BathroomType.getByCode(input.getBathroomType());
        } catch (IllegalArgumentException e) {
            log.error("Invalid bed size or bathroom type: {}", e.getMessage());
            return CheckAvailableRoomOutput.builder().ids(List.of()).build();
        }

        // Find rooms by bed size and bathroom type
        List<Room> roomsByBedAndBathroom = roomRepository.findRoomsByBedSizeAndBathroomType(bedSize, bathroomType);

        // Filter out rooms that have conflicting bookings
        List<Room> availableRooms = roomsByBedAndBathroom.stream()
                .filter(room -> !bookingRepository.existsByRoomAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        room, input.getEndDate(), input.getStartDate()))
                .toList();

        // Extract room IDs from available rooms
        List<String> availableRoomIds = availableRooms.stream()
                .map(room -> room.getId().toString())
                .collect(Collectors.toList());

        CheckAvailableRoomOutput output = CheckAvailableRoomOutput.builder()
                .ids(availableRoomIds)
                .build();

        log.info("End checkAvailableRoom with output: {}", output);
        return output;
    }

    @Override
    public GetRoomBasicInfoOutput getRoomBasicInfo(GetRoomBasicInfoInput input) {
        UUID roomId = input.getRoomId();
        Room room = roomRepository.findByIdWithBeds(roomId).orElseThrow(() -> new HotelException("Room not found"));

//        List<LocalDate> datesOccupied = bookingRepository.findAllByRoomId(roomId).stream()
//                .flatMap(booking ->
//                        booking
//                                .getStartDate()
//                                .datesUntil(booking.getEndDate().plusDays(1))
//                                .toList().stream())
//                .distinct()
//                .collect(Collectors.toList());

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

        return GetRoomBasicInfoOutput.builder()
                .id(room.getId())
                .price(room.getPrice())
                .floor(room.getFloor())
                .bedSize(room.getBeds().stream().findFirst().map(Bed::getBedSize).orElse(null))
                .bathroomType(room.getBathroomType())
                .datesOccupied(datesOccupied)
                .build();
    }



}