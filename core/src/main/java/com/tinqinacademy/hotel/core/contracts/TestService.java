package com.tinqinacademy.hotel.core.contracts;

import com.tinqinacademy.hotel.persistence.model.Bed;
import com.tinqinacademy.hotel.persistence.model.Room;
import com.tinqinacademy.hotel.persistence.model.User;
import com.tinqinacademy.hotel.persistence.model.Guest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TestService {

    // Bed
    Bed saveBed(Bed bed);
    Optional<Bed> findByIdBed(UUID id);
    Bed updateBed(Bed bed);
    void deleteBed(UUID id);
    List<Bed> findAllBeds();
    long countBeds();
    void deleteAllBeds();

    // Room
    Room saveRoom(Room room);
    Optional<Room> findByIdRoom(UUID id);
    Room updateRoom(Room room);
    void deleteRoom(UUID id);
    List<Room> findAllRooms();
    long countRooms();
    void deleteAllRooms();

    // User
    User saveUser(User user);
    Optional<User> findByIdUser(UUID id);
    User updateUser(User user);
    void deleteUser(UUID id);
    List<User> findAllUsers();
    long countUsers();
    void deleteAllUsers();

    // Guest
    Guest saveGuest(Guest guest);
    Optional<Guest> findByIdGuest(UUID id);
    Guest updateGuest(Guest guest);
    void deleteGuest(UUID id);
    List<Guest> findAllGuests();
    long countGuests();
    void deleteAllGuests();

    // Booking
    // TODO
}
