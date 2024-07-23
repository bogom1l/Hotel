package com.tinqinacademy.hotel.persistence.configuration;


import com.tinqinacademy.hotel.persistence.model.*;
import com.tinqinacademy.hotel.persistence.model.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.model.enums.BedSize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


/**
 * DataSeeder class is responsible for seeding initial data into the database when the application starts.
 * The class is responsible for creating:
 * - beds with unique bed sizes
 * - random rooms if they do not already exist
 * - TODO ...
 */
/*
@Slf4j
@Component
@Order(1)
public class DataSeeder implements ApplicationRunner {

    private final JdbcTemplate jdbcTemplate;
    private final BedRepository bedRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final GuestRepository guestRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public DataSeeder(JdbcTemplate jdbcTemplate, BedRepository bedRepository, RoomRepository roomRepository, UserRepository userRepository, GuestRepository guestRepository, BookingRepository bookingRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.bedRepository = bedRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.guestRepository = guestRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initializeBeds();  // generate beds, each with its unique bedSize (max 5)
        initializeRooms(); // generate 3 random rooms on the start of application (something like seed) (if there are no rooms already)
        initializeUsers();
        initializeGuests();
        initializeBookings();
    }

    private void initializeBeds() {
        log.info("Started DataSeeder's initializeBeds.");

        // if bedSize.getCode() is empty, skip it, for example: "UNKNOWN"
        List<BedSize> validBedSizes = Arrays.stream(BedSize.values())
                .filter(bedSize -> !bedSize.getCode().isEmpty())
                .toList();

        for (BedSize bedSize : validBedSizes) {
            // count of the existing beds without an already set bedSize (SINGLE, DOUBLE, SMALL_DOUBLE, KING_SIZE, QUEEN_SIZE)
            String query = "SELECT COUNT(*) FROM beds WHERE bed_size = ?::bed_size_enum";
            Integer count = jdbcTemplate.queryForObject(query, Integer.class, bedSize.toString());

            // if current bedSize is not found, add a new bed with that bedSize
            if (count == null || count == 0) {
                saveBed(bedSize);
                log.info("DataSeeder's initializeBeds added a bed with bedSize - {}.", bedSize);
            }
            // else if a bed with current bedSize already exists, do nothing
        }

        log.info("Ended DataSeeder's initializeBeds.");
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
        log.info("Started DataSeeder's initializeRooms.");

        String query = "SELECT COUNT(*) FROM rooms";
        Integer roomsCount = jdbcTemplate.queryForObject(query, Integer.class);

        // if there are any rooms -> exit method
        if (roomsCount != null && roomsCount != 0) {
            log.info("Ended DataSeeder's initializeRooms without creating any randomized rooms.");
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

        log.info("Ended DataSeeder's initializeRooms - created 3 randomized rooms with randomly shuffled existing beds.");
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


    // ----- GPT Generated sample seeding data -----

    private void initializeUsers() {
        log.info("Started DataSeeder's initializeUsers.");

        String query = "SELECT COUNT(*) FROM users";
        Integer userCount = jdbcTemplate.queryForObject(query, Integer.class);

        if (userCount != null && userCount != 0) {
            log.info("Ended DataSeeder's initializeUsers without creating any users.");
            return;
        }

        saveUser(UUID.randomUUID(), "john.doe@example.com", "password123", "John", "Doe", "1234567890", LocalDate.of(1990, 6, 3));
        saveUser(UUID.randomUUID(), "jane.smith@example.com", "password456", "Jane", "Smith", "0987654321", LocalDate.of(1992, 2, 4));

        log.info("Ended DataSeeder's initializeUsers - created initial users.");
    }

    private void saveUser(UUID id, String email, String password, String firstName, String lastName, String phoneNumber, LocalDate birthdate) {
        User user = User.builder()
                .id(id)
                .email(email)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .birthdate(birthdate)
                .build();

        userRepository.save(user);
    }

    private void initializeGuests() {
        log.info("Started DataSeeder's initializeGuests.");

        String query = "SELECT COUNT(*) FROM guests";
        Integer guestCount = jdbcTemplate.queryForObject(query, Integer.class);

        if (guestCount != null && guestCount != 0) {
            log.info("Ended DataSeeder's initializeGuests without creating any guests.");
            return;
        }

        saveGuest(UUID.randomUUID(), "Alice", "Johnson", "1234567890", "ID123", LocalDate.of(2025, 1, 1), "Authority1", LocalDate.of(2015, 1, 1), LocalDate.of(1985, 1, 1));
        saveGuest(UUID.randomUUID(), "Bob", "Brown", "0987654321", "ID456", LocalDate.of(2025, 2, 2), "AuthorityBG", LocalDate.of(2015, 2, 2), LocalDate.of(1986, 2, 2));

        log.info("Ended DataSeeder's initializeGuests - created initial guests.");
    }

    private void saveGuest(UUID id, String firstName, String lastName, String phoneNumber, String idCardNumber, LocalDate idCardValidity, String idCardIssueAuthority, LocalDate idCardIssueDate, LocalDate birthdate) {
        Guest guest = Guest.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .idCardNumber(idCardNumber)
                .idCardValidity(idCardValidity)
                .idCardIssueAuthority(idCardIssueAuthority)
                .idCardIssueDate(idCardIssueDate)
                .birthdate(birthdate)
                .build();

        guestRepository.save(guest);
    }

    private void initializeBookings() {
        log.info("Started DataSeeder's initializeBookings.");

        String query = "SELECT COUNT(*) FROM bookings";
        Integer bookingCount = jdbcTemplate.queryForObject(query, Integer.class);

        if (bookingCount != null && bookingCount != 0) {
            log.info("Ended DataSeeder's initializeBookings without creating any bookings.");
            return;
        }

        UUID roomId = roomRepository.findAll().getFirst().getId();
        UUID userId = userRepository.findAll().getFirst().getId();
        Set<Guest> guests = new HashSet<>(guestRepository.findAll());

        saveBooking(UUID.randomUUID(), roomId, userId, LocalDate.now(), LocalDate.now().plusDays(5), BigDecimal.valueOf(500), guests);

        log.info("Ended DataSeeder's initializeBookings - created initial bookings.");
    }

    private void saveBooking(UUID id, UUID roomId, UUID userId, LocalDate startDate, LocalDate endDate, BigDecimal totalPrice, Set<Guest> guests) {
        Booking booking = Booking.builder()
                .id(id)
                .roomId(roomId)
                .userId(userId)
                .startDate(startDate)
                .endDate(endDate)
                .totalPrice(totalPrice)
                .guests(guests)
                .build();

        bookingRepository.save(booking);
    }
}

*/