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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Database Seeder
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
        // else -> there are no rooms => generate 3 random rooms:

        List<BathroomType> validBathroomTypes = Arrays.stream(BathroomType.values())
                .filter(type -> !type.getCode().isEmpty())
                .toList();

        for (int i = 0; i < 3; i++) {
            // Randomly generate room details
            BigDecimal randomPrice = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(80.00, 800.00));
            Integer randomFloor = ThreadLocalRandom.current().nextInt(1, 6);
            String randomRoomNumber = generateRoomNumber(); // Use the new method to generate a valid room number

            BathroomType randomBathroomType = validBathroomTypes.get(
                    ThreadLocalRandom.current().nextInt(validBathroomTypes.size())
            );

            saveRoom(randomPrice, randomFloor, randomRoomNumber, randomBathroomType);
        }

        log.info("Ended DataInitializer for rooms.");
    }

    private void saveRoom(BigDecimal price, int floor, String roomNumber, BathroomType bathroomType) {
        Room room = Room.builder()
                .id(UUID.randomUUID())
                .price(price)
                .floor(floor)
                .roomNumber(roomNumber)
                .bathroomType(bathroomType)
                .build();

        roomRepository.save(room);
    }

    private String generateRoomNumber() {
        // Define allowed starting characters (A-Y)
        char[] allowedStartChars = "ABCDEFGHIJKLMNOPQRSTUVWXY".toCharArray();

        // Randomly select the starting character
        char startChar = allowedStartChars[ThreadLocalRandom.current().nextInt(allowedStartChars.length)];

        // Generate a random room number in the range 100 to 999
        int roomNumber = ThreadLocalRandom.current().nextInt(100, 1000);

        // Format the room number
        return String.format("%c%03d", startChar, roomNumber);
    }

}
