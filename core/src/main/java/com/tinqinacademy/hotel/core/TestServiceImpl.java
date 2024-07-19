package com.tinqinacademy.hotel.core;

import com.tinqinacademy.hotel.core.contracts.TestService;
import com.tinqinacademy.hotel.persistence.model.Bed;
import com.tinqinacademy.hotel.persistence.model.Room;
import com.tinqinacademy.hotel.persistence.model.User;
import com.tinqinacademy.hotel.persistence.repository.contracts.BedRepository;
import com.tinqinacademy.hotel.persistence.repository.contracts.RoomRepository;
import com.tinqinacademy.hotel.persistence.repository.contracts.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class TestServiceImpl implements TestService {

    private final UserRepository userRepository;
    private final BedRepository bedRepository;
    private final RoomRepository roomRepository;

    public TestServiceImpl(UserRepository userRepository, BedRepository bedRepository, RoomRepository roomRepository) {
        this.userRepository = userRepository;
        this.bedRepository = bedRepository;
        this.roomRepository = roomRepository;
    }

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


}
