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
        //...should throw Exception if the BedSize's code and capacity don't match...
        return findById(bed.getId()).orElse(null);
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
        // i think it returns array of ids and the variable becomes: List<Bed> {bed_id1, bed_id2, ...}
        return beds.isEmpty() ? Optional.empty() : Optional.of(beds.getFirst());
    }

    @Override
    public Bed update(Bed bed) {
        String query = "UPDATE beds SET capacity = ?, bed_size = ?::bed_size_enum WHERE id = ?";
        jdbcTemplate.update(query, bed.getCapacity(), bed.getBedSize().toString(), bed.getId());
        return findById(bed.getId()).orElse(null);
    }

    @Override
    public void deleteById(UUID id) {
        String deleteFromRoomsBedsQuery = "DELETE FROM rooms_beds WHERE bed_id = ?";
        jdbcTemplate.update(deleteFromRoomsBedsQuery, id);

        String deleteFromBedsQuery = "DELETE FROM beds WHERE id = ?";
        jdbcTemplate.update(deleteFromBedsQuery, id);
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
        String deleteAllFromRoomsBedsQuery = "DELETE FROM rooms_beds";
        jdbcTemplate.update(deleteAllFromRoomsBedsQuery);

        String deleteAllFromBedsQuery = "DELETE FROM beds";
        jdbcTemplate.update(deleteAllFromBedsQuery);
    }

}
