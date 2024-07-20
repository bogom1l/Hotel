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
 * DataSeeder class is responsible for seeding initial data into the database when the application starts.
 * The class is responsible for creating:
 * - beds with unique bed sizes
 * - random rooms if they do not already exist
 * - TODO ...
 */
@Slf4j
@Component
@Order(1)
public class DataSeeder implements ApplicationRunner {

    private final JdbcTemplate jdbcTemplate;
    private final BedRepository bedRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public DataSeeder(JdbcTemplate jdbcTemplate, BedRepository bedRepository, RoomRepository roomRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.bedRepository = bedRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initializeBeds();  // generate beds, each with its unique bedSize (max 5)
        initializeRooms(); // generate 3 random rooms on the start of application (something like seed) (if there are no rooms already)
    }

    private void initializeBeds() {
        log.info("Started initializeBeds in DataSeeder.");

        // if bedSize.getCode() is empty, skip it, for example: "UNKNOWN"
        List<BedSize> validBedSizes = Arrays.stream(BedSize.values())
                .filter(bedSize -> !bedSize.getCode().isEmpty())
                .toList();

        // for each BedSize Enum
        for (BedSize bedSize : validBedSizes) {
            // count of the existing beds without an already set bedSize (SINGLE, DOUBLE, SMALL_DOUBLE, KING_SIZE, QUEEN_SIZE)
            String query = "SELECT COUNT(*) FROM beds WHERE bed_size = ?::bed_size_enum";
            Integer count = jdbcTemplate.queryForObject(query, Integer.class, bedSize.toString());

            // if a bed with current bedSize already exists, do nothing
            if (count != null && count != 0) {
                continue;
            }
            // else -> means that a bed with current bedSize doesn't already exist, so add one
            saveBed(bedSize);
        }

        log.info("Ended initializeBeds in DataSeeder.");
    }

    private void saveBed(BedSize bedSize) {
        Bed bed = Bed.builder()
                .id(UUID.randomUUID())
                .bedSize(bedSize)
                .capacity(bedSize.getCapacity())
                .build();

        bedRepository.save(bed);
    }

    private void initializeRooms() {
        log.info("Started initializeRooms in DataSeeder.");

        String query = "SELECT COUNT(*) FROM rooms";
        Integer roomsCount = jdbcTemplate.queryForObject(query, Integer.class);

        // if there are any rooms -> exit method
        if (roomsCount != null && roomsCount != 0) {
            log.info("Ended initializeRooms in DataSeeder without creating any randomized rooms.");
            return;
        }
        // else -> there are no rooms => generate 3 random rooms:

        List<BathroomType> validBathroomTypes = Arrays.stream(BathroomType.values())
                .filter(type -> !type.getCode().isEmpty())
                .toList();

        for (int i = 0; i < 3; i++) {
            BigDecimal randomPrice = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(80.00, 800.00));
            int randomFloor = ThreadLocalRandom.current().nextInt(1, 6);
            String randomRoomNumber = generateRoomNumber();

            BathroomType randomBathroomType = validBathroomTypes.get(
                    ThreadLocalRandom.current().nextInt(validBathroomTypes.size())
            );

            saveRoom(randomPrice, randomFloor, randomRoomNumber, randomBathroomType);
        }

        log.info("Ended initializeRooms in DataSeeder - created 3 randomized rooms.");
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

    // generates a random room number in the format A123
    private String generateRoomNumber() {
        char[] allowedSymbols = "ABCDEFGHIJKLMNOPQRSTUVWXY".toCharArray();
        char symbol = allowedSymbols[ThreadLocalRandom.current().nextInt(allowedSymbols.length)];
        int n = ThreadLocalRandom.current().nextInt(100, 1000);

        return String.format("%c%03d", symbol, n);
    }

}
