package com.tinqinacademy.hotel.persistence.configuration;

import com.tinqinacademy.hotel.persistence.model.Bed;
import com.tinqinacademy.hotel.persistence.model.Room;
import com.tinqinacademy.hotel.persistence.model.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.model.enums.BedSize;
import com.tinqinacademy.hotel.persistence.repository.contracts.BedRepository;
import com.tinqinacademy.hotel.persistence.repository.contracts.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;


/**
 Database Seeder
 */
@Slf4j
@Component
@Order(1)
public class DataInitializer implements ApplicationRunner {
    
    private final JdbcTemplate jdbcTemplate;
    private final BedRepository bedRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public DataInitializer(JdbcTemplate jdbcTemplate, BedRepository bedRepository, RoomRepository roomRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.bedRepository = bedRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initializeBeds();
        // TODO: initializeRooms();
    }

    // generate beds, each with its unique bedSize
    private void initializeBeds() {
        log.info("Started DataInitializer for beds.");

        // for each BedSize Enum
        for (BedSize bedSize : BedSize.values()) {
            // if bedSize.getCode() is empty, skip it, for example: "UNKNOWN"
            if (bedSize.getCode().isEmpty()) {
                continue;
            }

            // count of the existing beds without an already set bedSize (SINGLE, DOUBLE, SMALLDOUBLE, KINGSIZE, QUEENSIZE)
            String query = "SELECT COUNT(*) FROM beds WHERE bed_size = ?::bed_size_enum";
            Integer count = jdbcTemplate.queryForObject(query, Integer.class, bedSize.toString());

            // if a bed with current bedSize already exists, do nothing
            if (count != null && count != 0) {
                continue;
            }

            // else -> means that a bed with current bedSize doesn't already exist, so add one
            Bed bed = Bed.builder()
                    .id(UUID.randomUUID())
                    .bedSize(bedSize)
                    .capacity(bedSize.getCapacity())
                    .build();

            bedRepository.save(bed);
        }

        // TODO: ? update

        log.info("Ended DataInitializer for beds.");
    }

    // generate random rooms on the start of application (something like seed)
    private void initializeRooms() {
        long count = roomRepository.count();

        if (count == 0) {
            List<Bed> allBeds = bedRepository.findAll();
            List<Bed> randomBeds = generateRandomBeds(allBeds);

            saveRoom(BigDecimal.valueOf(100.00), 1, "101", BathroomType.PRIVATE, randomBeds);
            saveRoom(BigDecimal.valueOf(150.00), 2, "201", BathroomType.SHARED, randomBeds);

            // Add more rooms as needed
        }
    }
    private void saveRoom(BigDecimal price, int floor, String roomNumber, BathroomType bathroomType, List<Bed> beds) {
        Room room = Room.builder()
                .id(UUID.randomUUID())
                .price(price)
                .floor(floor)
                .roomNumber(roomNumber)
                .bathroomType(bathroomType)
                .beds(beds)
                .build();

        roomRepository.save(room);
    }

    private List<Bed> generateRandomBeds(List<Bed> allBeds) {
        // select between 1 and (4 or allBeds.size())
        int numBeds = ThreadLocalRandom.current().nextInt(1, Math.min(4, allBeds.size()) + 1);
        List<Bed> randomBeds = new ArrayList<>();
        // select randomized beds
        for (int i = 0; i < numBeds; i++) {
            Bed randomBed = allBeds.get(ThreadLocalRandom.current().nextInt(allBeds.size()));
            randomBeds.add(randomBed);
        }
        return randomBeds;
    }

    /*
    private void initializeRooms() {
        String query = "SELECT COUNT(*) FROM rooms";
        Integer count = jdbcTemplate.queryForObject(query, Integer.class);

        if (count == null || count == 0) {
            List<Bed> beds = bedRepository.findAll();

            // Insert sample rooms
            insertRoom(UUID.randomUUID(), BigDecimal.valueOf(100.00), 1, "A101", BathroomType.PRIVATE, beds.subList(0, Math.min(2, beds.size())));
            insertRoom(UUID.randomUUID(), BigDecimal.valueOf(150.00), 2, "B201", BathroomType.SHARED, beds.subList(Math.min(2, beds.size()), Math.min(4, beds.size())));

            // Add more rooms as needed
        }
    }

    private void insertRoom(UUID roomId, BigDecimal price, int floor, String roomNumber, BathroomType bathroomType, List<Bed> beds) {
        String roomQuery = "INSERT INTO rooms (id, price, floor, room_number, bathroom_type) VALUES (?, ?, ?, ?, ?::bathroom_type_enum)";
        jdbcTemplate.update(roomQuery, roomId, price, floor, roomNumber, bathroomType.toString());

        String bedsForRoomQuery = "INSERT INTO rooms_beds (room_id, bed_id) VALUES (?, ?)";
        for (Bed bed : beds) {
            jdbcTemplate.update(bedsForRoomQuery, roomId, bed.getId());
        }
    }
    */

}
