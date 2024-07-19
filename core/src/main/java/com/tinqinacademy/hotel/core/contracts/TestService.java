package com.tinqinacademy.hotel.core.contracts;

import com.tinqinacademy.hotel.persistence.model.Bed;
import com.tinqinacademy.hotel.persistence.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TestService {

    // Bed
    Bed saveBed(Bed bed);
    Optional<Bed> findByIdBed(UUID id);
    Bed updateBed(Bed bed);
    void deleteByIdBed(UUID id);
    List<Bed> findAllBeds();
    long countBeds();
    void deleteAll();

    // User
    List<User> findAllUsers();

}
