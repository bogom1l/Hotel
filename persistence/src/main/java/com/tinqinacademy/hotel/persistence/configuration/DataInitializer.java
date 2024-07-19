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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


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
        initializeBeds();  // generate beds, each with its unique bedSize
        initializeRooms(); // generate 3 random rooms on the start of application (something like seed) (if there are no rooms already)
    }

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

    private void initializeRooms() {
        log.info("Started DataInitializer for rooms.");

        String query = "SELECT COUNT(*) FROM rooms";
        Integer roomsCount = jdbcTemplate.queryForObject(query, Integer.class);

        // if there are any rooms -> exit method
        if (roomsCount != null && roomsCount != 0) {
            return;
        }
        // else -> there are no beds => generate 3 random rooms:

        List<Bed> allBeds = bedRepository.findAll();

        List<BathroomType> validBathroomTypes = Arrays.stream(BathroomType.values())
                .filter(type -> !type.getCode().isEmpty())
                .toList();

        for (int i = 0; i < 3; i++) {
            // Randomly generate room details
            BigDecimal randomPrice = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(80.00, 800.00));
            Integer randomFloor = ThreadLocalRandom.current().nextInt(1, 6);
            String randomRoomNumber = String.format("%03d", ThreadLocalRandom.current().nextInt(1, 1000)); // Room number as "001", "042", etc.

            //TODO BathroomType randomBathroomType = BathroomType.values()[ThreadLocalRandom.current().nextInt(BathroomType.values().length)];

            BathroomType randomBathroomType = validBathroomTypes.get(
                    ThreadLocalRandom.current().nextInt(validBathroomTypes.size())
            );

            //TODO List<Bed> randomBeds = generateRandomBeds(allBeds);

            saveRoom(randomPrice, randomFloor, randomRoomNumber, randomBathroomType); //TODO, randomBeds);
        }

        log.info("Ended DataInitializer for rooms.");
    }

    private void saveRoom(BigDecimal price, int floor, String roomNumber, BathroomType bathroomType){//TODO, List<Bed> beds) {
        Room room = Room.builder()
                .id(UUID.randomUUID())
                .price(price)
                .floor(floor)
                .roomNumber(roomNumber)
                .bathroomType(bathroomType)
                //TODO .beds(beds)
                .build();

        roomRepository.save(room);
    }

    //TODO
    /*
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
*/
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
