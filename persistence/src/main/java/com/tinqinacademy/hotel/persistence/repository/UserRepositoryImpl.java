package com.tinqinacademy.hotel.persistence.repository;

import com.tinqinacademy.hotel.persistence.models.User;
import com.tinqinacademy.hotel.persistence.repository.contracts.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAll() {

        String query = "select * from users";

        return jdbcTemplate.query(query, (rs, rowNum) -> {
            return User.builder()
                    .id(UUID.fromString(rs.getString("id")))
                    .email(rs.getString("email"))
                    .password(rs.getString("password"))
                    .firstName(rs.getString("firstName"))
                    .lastName(rs.getString("lastName"))
                    .phoneNumber(rs.getString("phoneNumber"))
                    .birthdate(LocalDate.parse(rs.getString("birthdate")))
                    .build();
        });

    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public int save(User user) {
        return 0;
    }
}