package com.tinqinacademy.hotel.persistence.repository;

import com.tinqinacademy.hotel.persistence.model.Bed;
import com.tinqinacademy.hotel.persistence.model.Room;
import com.tinqinacademy.hotel.persistence.model.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.model.enums.BedSize;
import com.tinqinacademy.hotel.persistence.repository.contracts.RoomRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Repository
public class RoomRepositoryImpl implements RoomRepository {

    private final JdbcTemplate jdbcTemplate;

    public RoomRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private List<Bed> fetchAllBeds() {
        String query = "SELECT * FROM beds";
        return jdbcTemplate.query(query, (rs, rowNum) -> Bed.builder()
                .id(UUID.fromString(rs.getString("id")))
                .capacity(rs.getInt("capacity"))
                .bedSize(BedSize.getByCode(rs.getString("bed_size")))
                .build());
    }

    private List<Bed> selectRandomBeds(List<Bed> allBeds) {
        int numberOfBeds = ThreadLocalRandom.current().nextInt(1, 4);
        Collections.shuffle(allBeds); // Shuffle the list to get a random selection
        return allBeds.subList(0, numberOfBeds);
    }

    @Override
    public Room save(Room room) {

        List<Bed> allBeds = fetchAllBeds();
        List<Bed> randomBeds = selectRandomBeds(allBeds);

        room.setBeds(randomBeds); // there should already be beds, so no need to check if randomBeds.isEmpty() (for now)

        String query = "INSERT INTO rooms (id, price, floor, room_number, bathroom_type) VALUES (?, ?, ?, ?, ?::bathroom_type_enum)";
        jdbcTemplate.update(query, room.getId(), room.getPrice(), room.getFloor(), room.getRoomNumber(), room.getBathroomType().toString());

        saveInRoomsBedsTable(room.getId(), room.getBeds()); // And insert the beds in the room

        return room;
    }

    private void saveInRoomsBedsTable(UUID roomId, List<Bed> beds) {
        String query = "INSERT INTO rooms_beds (room_id, bed_id) VALUES (?, ?)";
        for (Bed bed : beds) {
            jdbcTemplate.update(query, roomId, bed.getId());
        }
    }

    private RowMapper<Room> roomRowMapper() {
        return (rs, rowNum) -> Room.builder()
                .id(UUID.fromString(rs.getString("id")))
                .price(rs.getBigDecimal("price"))
                .floor(rs.getInt("floor"))
                .roomNumber(rs.getString("room_number"))
                .bathroomType(BathroomType.getByCode(rs.getString("bathroom_type")))
                .beds(fetchBedsForRoom(UUID.fromString(rs.getString("id"))))
                .build();
    }

    private List<Bed> fetchBedsForRoom(UUID roomId) {
        String query = "SELECT b.id, b.capacity, b.bed_size FROM beds b JOIN rooms_beds rb ON b.id = rb.bed_id WHERE rb.room_id = ?";

        return jdbcTemplate.query(query, new Object[]{roomId}, (rs, rowNum) -> Bed.builder()
                .id(UUID.fromString(rs.getString("id")))
                .capacity(rs.getInt("capacity"))
                .bedSize(BedSize.getByCode(rs.getString("bed_size")))
                .build());
    }

    @Override
    public Optional<Room> findById(UUID id) {
        String query = "SELECT * FROM rooms WHERE id = ?";
        List<Room> rooms = jdbcTemplate.query(query, new Object[]{id}, roomRowMapper());
        return rooms.isEmpty() ? Optional.empty() : Optional.of(rooms.getFirst());
    }

    @Override
    public Room update(Room entity) {
        return null; //TODO
    }

    @Override
    public void deleteById(UUID id) {
        //TODO
    }

    @Override
    public List<Room> findAll() {
        return List.of(); //TODO
    }

    @Override
    public long count() {
        String query = "SELECT COUNT(*) FROM rooms";
        return jdbcTemplate.queryForObject(query, Long.class);
    }

    @Override
    public void deleteAll() {
        String deleteAllFromRoomsBedQuery = "DELETE FROM rooms_beds";
        jdbcTemplate.update(deleteAllFromRoomsBedQuery);

        String deleteAllFromRoomsQuery = "DELETE FROM rooms";
        jdbcTemplate.update(deleteAllFromRoomsQuery);
    }
}
