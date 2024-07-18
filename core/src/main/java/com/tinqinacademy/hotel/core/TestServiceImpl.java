package com.tinqinacademy.hotel.core;

import com.tinqinacademy.hotel.core.contracts.TestService;
import com.tinqinacademy.hotel.persistence.model.Bed;
import com.tinqinacademy.hotel.persistence.model.User;
import com.tinqinacademy.hotel.persistence.repository.contracts.BedRepository;
import com.tinqinacademy.hotel.persistence.repository.contracts.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TestServiceImpl implements TestService {

    private final UserRepository userRepository;
    private final BedRepository bedRepository;

    public TestServiceImpl(UserRepository userRepository, BedRepository bedRepository) {
        this.userRepository = userRepository;
        this.bedRepository = bedRepository;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<Bed> findAllBeds() {
        return bedRepository.findAll();
    }

    @Override
    public void addBed(Bed bed) {
        bedRepository.save(bed);
    }

}
