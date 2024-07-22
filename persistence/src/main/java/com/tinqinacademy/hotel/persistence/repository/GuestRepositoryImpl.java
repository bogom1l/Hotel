package com.tinqinacademy.hotel.persistence.repository;

import com.tinqinacademy.hotel.persistence.model.Guest;
import com.tinqinacademy.hotel.persistence.model.User;
import com.tinqinacademy.hotel.persistence.repository.contracts.GuestRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class GuestRepositoryImpl implements GuestRepository {

    private final JdbcTemplate jdbcTemplate;

    public GuestRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Guest save(Guest entity) {
        String query =
                "INSERT into guests (id, first_name, last_name, phone_number, id_card_number, id_card_validity, id_card_issue_authority, id_card_issue_date, birthdate) VALUES (?, ?, ?, ?, ?, ?, ? ,? , ?)";
        jdbcTemplate.update(query, entity.getId(), entity.getFirstName(), entity.getLastName(),
                entity.getPhoneNumber(), entity.getIdCardNumber(), entity.getIdCardValidity(),
                entity.getIdCardIssueAuthority(), entity.getIdCardIssueDate(), entity.getBirthdate());

        return entity;
    }

    private RowMapper<Guest> guestRowMapper() {
        return (rs, rowNum) -> Guest.builder()
                .id(UUID.fromString(rs.getString("id")))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .phoneNumber(rs.getString("phone_number"))
                .idCardNumber(rs.getString("id_card_number"))
                .idCardValidity(rs.getDate("id_card_validity").toLocalDate())
                .idCardIssueAuthority(rs.getString("id_card_issue_authority"))
                .idCardIssueDate(rs.getDate("id_card_issue_date").toLocalDate())
                .birthdate(rs.getDate("birthdate").toLocalDate())
                .build();
    }

    @Override
    public Optional<Guest> findById(UUID id) {
        String query = "SELECT * FROM guests WHERE id = ?";
        try {
            Guest guest = jdbcTemplate.queryForObject(query, new Object[]{id}, guestRowMapper());
            return Optional.ofNullable(guest);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Guest update(Guest entity) {
        String query = "UPDATE guests SET first_name = ?, last_name = ?, phone_number = ?, id_card_number = ?, id_card_validity = ?, id_card_issue_authority = ?, id_card_issue_date = ?, birthdate = ? WHERE id = ?";
        jdbcTemplate.update(query, entity.getFirstName(), entity.getLastName(), entity.getPhoneNumber(),
                entity.getIdCardNumber(), entity.getIdCardValidity(), entity.getIdCardIssueAuthority(),
                entity.getIdCardIssueDate(), entity.getBirthdate(), entity.getId());

        return entity;
    }

    @Override
    public void deleteById(UUID id) {
        String query = "DELETE FROM guests WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

    @Override
    public List<Guest> findAll() {
        String query = "SELECT * FROM guests";
        return jdbcTemplate.query(query, guestRowMapper());
    }

    @Override
    public long count() {
        String query = "SELECT count(*) from guests";
        return jdbcTemplate.queryForObject(query, Long.class);
    }

    @Override
    public void deleteAll() {
        String query = "DELETE FROM guests";
        jdbcTemplate.update(query);
    }
}
