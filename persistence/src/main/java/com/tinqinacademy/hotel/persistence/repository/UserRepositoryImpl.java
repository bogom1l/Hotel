package com.tinqinacademy.hotel.persistence.repository;

import com.tinqinacademy.hotel.persistence.model.User;
import com.tinqinacademy.hotel.persistence.repository.contracts.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User save(User user) {
        String query =
                "INSERT INTO users (id, email, password, first_name, last_name, phone_number, birthdate) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(query,
                user.getId(), user.getEmail(), user.getPassword(), user.getFirstName(),
                user.getLastName(), user.getPhoneNumber(), user.getBirthdate());

        return user;
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> User.builder()
                .id(UUID.fromString(rs.getString("id")))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .phoneNumber(rs.getString("phone_number"))
                .birthdate(rs.getDate("birthdate").toLocalDate())
                .build();
    }

    @Override
    public Optional<User> findById(UUID id) {
        String query = "SELECT * FROM users WHERE id = ?";
        try {
            User user = jdbcTemplate.queryForObject(query, new Object[]{id}, userRowMapper());
            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty();
        }
        // List<User> users = jdbcTemplate.query(query, new Object[]{id}, userRowMapper());
        // return users.isEmpty() ? Optional.empty() : Optional.of(users.getFirst());
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteAll() {

    }
}


