package com.tinqinacademy.hotel.core.contracts;

import com.tinqinacademy.hotel.persistence.models.Bed;
import com.tinqinacademy.hotel.persistence.models.User;

import java.util.List;

public interface TestService {

    List<User> findAllUsers();

    List<Bed> findAllBeds();

    void addBed(Bed bed);
}
