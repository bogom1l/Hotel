package com.tinqinacademy.hotel.persistence.configuration;


import com.tinqinacademy.hotel.persistence.model.*;
import com.tinqinacademy.hotel.persistence.model.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.model.enums.BedSize;
import com.tinqinacademy.hotel.persistence.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;


/*
    DataSeeder class is responsible for seeding initial data into the database when the application starts.
*/

@Slf4j
@Component
@Order(1)
public class DataSeeder implements ApplicationRunner {

    private final BedRepository bedRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final GuestRepository guestRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public DataSeeder(BedRepository bedRepository, RoomRepository roomRepository, UserRepository userRepository, GuestRepository guestRepository, BookingRepository bookingRepository) {
        this.bedRepository = bedRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.guestRepository = guestRepository;
        this.bookingRepository = bookingRepository;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        seedBeds();
        seedRooms();
        seedUsers();
        seedGuests();
        seedBookings();
    }

    private void seedBeds() {
        if (bedRepository.count() != 0) {
            log.info("DataSeeder - didn't seed any beds.");
            return;
        }

        Bed singleBed = Bed.builder()
                //.id(UUID.randomUUID())
                .bedSize(BedSize.SINGLE)
                .capacity(1)
                .build();


        Bed smallDoubleBed = Bed.builder()
                .bedSize(BedSize.SMALL_DOUBLE)
                .capacity(2)
                .build();

        Bed doubleBed = Bed.builder()
                .bedSize(BedSize.DOUBLE)
                .capacity(2)
                .build();

        Bed kingSizeBed = Bed.builder()
                .bedSize(BedSize.KING_SIZE)
                .capacity(2)
                .build();

        Bed queenSizeBed = Bed.builder()
                .bedSize(BedSize.QUEEN_SIZE)
                .capacity(2)
                .build();

        bedRepository.saveAll(List.of(singleBed, smallDoubleBed, doubleBed, kingSizeBed, queenSizeBed));
        log.info("DataSeeder - seeded beds.");
    }

    private void seedRooms() {
        if (roomRepository.count() != 0) {
            log.info("DataSeeder - didn't seed any rooms.");
            return;
        }

        Bed sampleBed1 = bedRepository.findAll().get(0);
        Bed sampleBed2 = bedRepository.findAll().get(1);
        Bed sampleBed3 = bedRepository.findAll().get(1);

        Room room1 = Room.builder()
                .price(new BigDecimal("100.00"))
                .floor(1)
                .roomNumber("A101")
                .bathroomType(BathroomType.PRIVATE)
                .beds(List.of(sampleBed1, sampleBed2))
                .build();

        Room room2 = Room.builder()
                .price(new BigDecimal("200.00"))
                .floor(2)
                .roomNumber("B227")
                .bathroomType(BathroomType.SHARED)
                .beds(List.of(sampleBed3))
                .build();

        roomRepository.saveAll(List.of(room1, room2));
        log.info("DataSeeder - seeded rooms.");
    }

    private void seedUsers() {
        if (userRepository.count() != 0) {
            log.info("DataSeeder - didn't seed any users.");
            return;
        }

        User user1 = User.builder()
                .email("user@example.com")
                .password("password")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("1234567890")
                .birthdate(LocalDate.of(1990, 1, 1))
                .build();

        User user2 = User.builder()
                .email("test@mail.com")
                .password("123456")
                .firstName("Bogi")
                .lastName("Stoev")
                .phoneNumber("0888888888")
                .birthdate(LocalDate.of(2001, 5, 11))
                .build();

        userRepository.saveAll(List.of(user1, user2));
        log.info("DataSeeder - seeded users.");
    }

    private void seedGuests() {
        if (guestRepository.count() != 0) {
            log.info("DataSeeder - didn't seed any guests.");
            return;
        }

        Guest guest1 = Guest.builder()
                .firstName("Jane")
                .lastName("Doe")
                .phoneNumber("0987654321")
                .idCardNumber("ID123456")
                .idCardValidity(LocalDate.of(2025, 1, 1))
                .idCardIssueAuthority("Authority1")
                .idCardIssueDate(LocalDate.of(2015, 1, 1))
                .birthdate(LocalDate.of(1995, 1, 1))
                .build();

        Guest guest2 = Guest.builder()
                .firstName("Alex")
                .lastName("Wick")
                .phoneNumber("0834913413")
                .idCardNumber("ID531333")
                .idCardValidity(LocalDate.of(2027, 7, 7))
                .idCardIssueAuthority("AuthorityB")
                .idCardIssueDate(LocalDate.of(2011, 9, 12))
                .birthdate(LocalDate.of(1999, 11, 25))
                .build();

        guestRepository.saveAll(List.of(guest1, guest2));
        log.info("DataSeeder - seeded guests.");
    }

    private void seedBookings() {
        if (bookingRepository.count() != 0) {
            log.info("DataSeeder - didn't seed any bookings.");
            return;
        }

        Room room = roomRepository.findAll().get(0);
        User user = userRepository.findAll().get(0);
        Guest guest = guestRepository.findAll().get(0);

        Booking booking = Booking.builder()
                .room(room)
                .user(user)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(3))
                .totalPrice(new BigDecimal("350.00"))
                .guests(Set.of(guest))
                .build();

        bookingRepository.save(booking);
        log.info("DataSeeder - seeded a booking.");
    }
}

