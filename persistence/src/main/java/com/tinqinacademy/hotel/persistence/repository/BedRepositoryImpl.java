package com.tinqinacademy.hotel.persistence.repository;

import com.tinqinacademy.hotel.persistence.models.Bed;
import com.tinqinacademy.hotel.persistence.models.enums.BedSize;
import com.tinqinacademy.hotel.persistence.repository.contracts.BedRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class BedRepositoryImpl implements BedRepository {

    private final JdbcTemplate jdbcTemplate;

    public BedRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Bed findById(UUID id) {
//        String query = "SELECT * FROM beds WHERE ...";
//
//        return jdbcTemplate.query(query, (rs, rowNum) -> {
//            return Bed.builder()
//                    .id(id)
//                    .capacity(Integer.valueOf(rs.getString("capacity")))
//                    .bedSize(BedSize.valueOf(rs.getString("bed_size")))
//                    .build();
//        });
        return null;
    }

    @Override
    public List<Bed> findAll() {
        String query = "SELECT * FROM beds";

        return jdbcTemplate.query(query, (rs, rowNum) -> {
            return Bed.builder()
                    .id(UUID.fromString(rs.getString("id")))
                    .capacity(Integer.valueOf(rs.getString("capacity")))
                    .bedSize(BedSize.valueOf(rs.getString("bed_size")))
                    .build();
        });
    }

    @Override
    public void save(Bed bed) {
        String query = "INSERT INTO beds (id, capacity, bed_size) VALUES (?, ?, ?::bed_size_enum)";
        jdbcTemplate.update(query, bed.getId(), bed.getCapacity(), bed.getBedSize().toString());
    }

    public void update(Bed bed) {

    }

    public void delete(UUID id) {

    }
}
