package com.tinqinacademy.hotel.core;

import com.tinqinacademy.hotel.core.contracts.TestService;
import com.tinqinacademy.hotel.persistence.model.Bed;
import com.tinqinacademy.hotel.persistence.model.User;
import com.tinqinacademy.hotel.persistence.repository.contracts.BedRepository;
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

    public TestServiceImpl(UserRepository userRepository, BedRepository bedRepository) {
        this.userRepository = userRepository;
        this.bedRepository = bedRepository;
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
    public void deleteByIdBed(UUID id) {
        bedRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
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


    // User


    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


}
