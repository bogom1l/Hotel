package com.tinqinacademy.hotel.core;

import com.tinqinacademy.hotel.core.contracts.TestService;
import com.tinqinacademy.hotel.persistence.model.*;
import com.tinqinacademy.hotel.persistence.model.getroombasicinfo.GetRoomBasicInfoInput;
import com.tinqinacademy.hotel.persistence.model.getroombasicinfo.GetRoomBasicInfoOutput;
import com.tinqinacademy.hotel.persistence.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class TestServiceImpl implements TestService {

    private final UserRepository userRepository;
    private final BedRepository bedRepository;
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final BookingRepository bookingRepository;

    public TestServiceImpl(UserRepository userRepository, BedRepository bedRepository, RoomRepository roomRepository, GuestRepository guestRepository, BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.bedRepository = bedRepository;
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.bookingRepository = bookingRepository;
    }
/*
    // Bed

    @Override
    public Bed saveBed(Bed bed) {
        return bedRepository.save(bed);
    }

    @Override
    public Optional<Bed> findByIdBed(UUID id) {
        return bedRepository.findById(id);
    }

    @Override
    public Bed updateBed(Bed bed) {
        return bedRepository.update(bed);
    }

    @Override
    public void deleteBed(UUID id) {
        bedRepository.deleteById(id);
    }

    @Override
    public void deleteAllBeds() {
        bedRepository.deleteAll();
    }

    @Override
    public List<Bed> findAllBeds() {
        return bedRepository.findAll();
    }

    @Override
    public long countBeds() {
        return bedRepository.count();
    }

    // Room

    @Override
    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Optional<Room> findByIdRoom(UUID id) {
        return roomRepository.findById(id);
    }

    @Override
    public Room updateRoom(Room room) {
        return roomRepository.update(room);
    }

    @Override
    public void deleteRoom(UUID id) {
        roomRepository.deleteById(id);
    }

    @Override
    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public long countRooms() {
        return roomRepository.count();
    }

    @Override
    public void deleteAllRooms() {
        roomRepository.deleteAll();
    }


    // User

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByIdUser(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.update(user);
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public long countUsers() {
        return userRepository.count();
    }

    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    // Guest

    @Override
    public Guest saveGuest(Guest guest) {
        return guestRepository.save(guest);
    }

    @Override
    public Optional<Guest> findByIdGuest(UUID id) {
        return guestRepository.findById(id);
    }

    @Override
    public Guest updateGuest(Guest guest) {
        return guestRepository.update(guest);
    }

    @Override
    public void deleteGuest(UUID id) {
        guestRepository.deleteById(id);
    }

    @Override
    public List<Guest> findAllGuests() {
        return guestRepository.findAll();
    }

    @Override
    public long countGuests() {
        return guestRepository.count();
    }

    @Override
    public void deleteAllGuests() {
        guestRepository.deleteAll();
    }

    // Booking

    @Override
    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Optional<Booking> findByIdBooking(UUID id) {
        return bookingRepository.findById(id);
    }

    @Override
    public Booking updateBooking(Booking booking) {
        return bookingRepository.update(booking);
    }

    @Override
    public void deleteBooking(UUID id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public List<Booking> findAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public long countBookings() {
        return bookingRepository.count();
    }

    @Override
    public void deleteAllBookings() {
        bookingRepository.deleteAll();
    }


    // - - - - - - - - - - - - - - - - - -

    @Override
    public GetRoomBasicInfoOutput getRoomBasicInfo(GetRoomBasicInfoInput input) {

        Room room = roomRepository.findById(input.getRoomId()).orElse(null);
        if(room == null) {
            return null;
        }

        List<Booking> bookings = bookingRepository.findByRoomId(input.getRoomId());

        List<LocalDate> datesOccupied = new ArrayList<>();
        for (Booking booking : bookings) {
            LocalDate startDate = booking.getStartDate();
            LocalDate endDate = booking.getEndDate();

            while (!startDate.isAfter(endDate)) {
                datesOccupied.add(startDate);
                startDate = startDate.plusDays(1);
            }
        }

        GetRoomBasicInfoOutput output = GetRoomBasicInfoOutput.builder()
                .id(input.getRoomId())
                .price(room.getPrice())
                .floor(room.getFloor())
                .bedSize(room.getBeds().getFirst().getBedSize())
                .bathroomType(room.getBathroomType())
                .bedCount(room.getBeds().size())
                .datesOccupied(datesOccupied)
                .build();

        return output;
    }

*/
}
