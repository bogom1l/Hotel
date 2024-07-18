package com.tinqinacademy.hotel.persistence.repository.contracts;

import com.tinqinacademy.hotel.persistence.models.Bed;

import java.util.List;

public interface BedRepository {
    List<Bed> findAll();

    void save(Bed bed);
}
