package com.tinqinacademy.hotel.persistence.repository;

import com.tinqinacademy.hotel.persistence.model.Bed;
import com.tinqinacademy.hotel.persistence.model.enums.BedSize;
import com.tinqinacademy.hotel.persistence.repository.contracts.BedRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class BedRepositoryImpl implements BedRepository {

    private final JdbcTemplate jdbcTemplate;

    public BedRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Bed save(Bed bed) {
        String query = "INSERT INTO beds (id, capacity, bed_size) VALUES (?, ?, ?::bed_size_enum)";
        jdbcTemplate.update(query, bed.getId(), bed.getCapacity(), bed.getBedSize().toString());
        // String query = "INSERT INTO beds (id, capacity, bed_size) VALUES (?, ?, ?)";
        // jdbcTemplate.update(query, bed.getId().toString(), bed.getCapacity(), bed.getBedSize().toString());
        return bed;
    }

    private RowMapper<Bed> bedRowMapper() {
        return (rs, rowNum) -> Bed.builder()
                .id(UUID.fromString(rs.getString("id")))
                .capacity(rs.getInt("capacity"))
                // .capacity(Integer.valueOf(rs.getString("capacity")))
                .bedSize(BedSize.getByCode(rs.getString("bed_size")))
                .build();
    }

    @Override
    public Optional<Bed> findById(UUID id) {
        String query = "SELECT * FROM beds WHERE id = ?";
        List<Bed> beds = jdbcTemplate.query(query, new Object[]{id}, bedRowMapper());
        // "new Object[]{id}" is probably some legacy thing and that's how it should be done.
        return beds.isEmpty() ? Optional.empty() : Optional.of(beds.getFirst());
    }

    @Override
    public Bed update(Bed bed) {
        String query = "UPDATE beds SET capacity = ?, bed_size = ?::bed_size_enum WHERE id = ?";
        jdbcTemplate.update(query, bed.getCapacity(), bed.getBedSize().toString(), bed.getId());
        return bed;
    }

    @Override
    public void deleteById(UUID id) {
        String query = "DELETE FROM beds WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

    @Override
    public List<Bed> findAll() {
        String query = "SELECT * FROM beds";
        return jdbcTemplate.query(query, bedRowMapper());
    }

    @Override
    public long count() {
        String query = "SELECT COUNT(*) FROM beds";
        return jdbcTemplate.queryForObject(query, Long.class);
    }

    @Override
    public void deleteAll() {
        String query = "DELETE FROM beds";
        jdbcTemplate.update(query);
    }

}
