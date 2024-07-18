package com.tinqinacademy.hotel.core.contracts;

import com.tinqinacademy.hotel.persistence.model.Bed;
import com.tinqinacademy.hotel.persistence.model.User;

import java.util.List;

public interface TestService {

    List<User> findAllUsers();

    List<Bed> findAllBeds();

    void addBed(Bed bed);
}
