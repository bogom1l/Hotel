package com.tinqinacademy.hotel.persistence.repository.contracts;

import com.tinqinacademy.hotel.persistence.models.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();
    User findById(int id);
    int save(User user);
}
