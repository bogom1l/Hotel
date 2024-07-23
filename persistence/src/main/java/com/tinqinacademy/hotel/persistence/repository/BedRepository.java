package com.tinqinacademy.hotel.persistence.repository;

import com.tinqinacademy.hotel.persistence.model.Bed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BedRepository extends JpaRepository<Bed, UUID> {
}
