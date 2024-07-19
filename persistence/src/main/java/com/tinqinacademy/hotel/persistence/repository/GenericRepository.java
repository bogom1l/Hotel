package com.tinqinacademy.hotel.persistence.repository;

import com.tinqinacademy.hotel.persistence.model.contracts.Entity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GenericRepository<T extends Entity> {
    T save (T entity);                   // C
    Optional<T> findById(UUID id);       // R
    T update (T entity);                 // U
    void deleteById(UUID id);            // D
    List<T> findAll();
    long count();
    void deleteAll();
    // boolean existsById(UUID id);
}
