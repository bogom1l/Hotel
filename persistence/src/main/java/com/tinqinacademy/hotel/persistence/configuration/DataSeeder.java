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
        Bed sampleBed3 = bedRepository.findAll().get(2);
        Bed sampleBed4 = bedRepository.findAll().get(3);
        Bed sampleBed5 = bedRepository.findAll().get(4);

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

        Room room3 = Room.builder()
                .price(new BigDecimal("150.00"))
                .floor(3)
                .roomNumber("C303")
                .bathroomType(BathroomType.PRIVATE)
                .beds(List.of(sampleBed4))
                .build();

        Room room4 = Room.builder()
                .price(new BigDecimal("250.00"))
                .floor(4)
                .roomNumber("D404")
                .bathroomType(BathroomType.SHARED)
                .beds(List.of(sampleBed5))
                .build();

        roomRepository.saveAll(List.of(room1, room2, room3, room4));
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

        User user3 = User.builder()
                .email("alice@example.com")
                .password("password123")
                .firstName("Alice")
                .lastName("Smith")
                .phoneNumber("2345678901")
                .birthdate(LocalDate.of(1985, 2, 15))
                .build();

        User user4 = User.builder()
                .email("bob@example.com")
                .password("password123")
                .firstName("Bob")
                .lastName("Johnson")
                .phoneNumber("3456789012")
                .birthdate(LocalDate.of(1978, 3, 20))
                .build();

        User user5 = User.builder()
                .email("charlie@example.com")
                .password("password123")
                .firstName("Charlie")
                .lastName("Brown")
                .phoneNumber("4567890123")
                .birthdate(LocalDate.of(1992, 4, 25))
                .build();

        User user6 = User.builder()
                .email("david@example.com")
                .password("password123")
                .firstName("David")
                .lastName("Williams")
                .phoneNumber("5678901234")
                .birthdate(LocalDate.of(1980, 5, 30))
                .build();

        User user7 = User.builder()
                .email("eve@example.com")
                .password("password123")
                .firstName("Eve")
                .lastName("Davis")
                .phoneNumber("6789012345")
                .birthdate(LocalDate.of(1995, 6, 5))
                .build();

        User user8 = User.builder()
                .email("frank@example.com")
                .password("password123")
                .firstName("Frank")
                .lastName("Miller")
                .phoneNumber("7890123456")
                .birthdate(LocalDate.of(1983, 7, 10))
                .build();

        User user9 = User.builder()
                .email("grace@example.com")
                .password("password123")
                .firstName("Grace")
                .lastName("Wilson")
                .phoneNumber("8901234567")
                .birthdate(LocalDate.of(1998, 8, 15))
                .build();

        User user10 = User.builder()
                .email("henry@example.com")
                .password("password123")
                .firstName("Henry")
                .lastName("Moore")
                .phoneNumber("9012345678")
                .birthdate(LocalDate.of(1975, 9, 20))
                .build();

        User user11 = User.builder()
                .email("isabel@example.com")
                .password("password123")
                .firstName("Isabel")
                .lastName("Taylor")
                .phoneNumber("0123456789")
                .birthdate(LocalDate.of(1987, 10, 25))
                .build();

        User user12 = User.builder()
                .email("jack@example.com")
                .password("password123")
                .firstName("Jack")
                .lastName("Anderson")
                .phoneNumber("1234509876")
                .birthdate(LocalDate.of(1991, 11, 30))
                .build();

        userRepository.saveAll(List.of(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user11, user12));
        log.info("DataSeeder - seeded users.");

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

        Guest guest3 = Guest.builder()
                .firstName("Bogi")
                .lastName("Stoev")
                .phoneNumber("0888919934")
                .idCardNumber("9999")
                .idCardValidity(LocalDate.of(2029, 9, 11))
                .idCardIssueAuthority("AuthorityB")
                .idCardIssueDate(LocalDate.of(2013, 2, 18))
                .birthdate(LocalDate.of(2001, 11, 16))
                .build();

        Guest guest4 = Guest.builder()
                .firstName("Michael")
                .lastName("Jordan")
                .phoneNumber("0912345678")
                .idCardNumber("ID789012")
                .idCardValidity(LocalDate.of(2028, 3, 15))
                .idCardIssueAuthority("AuthorityC")
                .idCardIssueDate(LocalDate.of(2018, 3, 15))
                .birthdate(LocalDate.of(1980, 2, 17))
                .build();

        Guest guest5 = Guest.builder()
                .firstName("Sarah")
                .lastName("Connor")
                .phoneNumber("0923456789")
                .idCardNumber("ID890123")
                .idCardValidity(LocalDate.of(2029, 4, 20))
                .idCardIssueAuthority("AuthorityD")
                .idCardIssueDate(LocalDate.of(2019, 4, 20))
                .birthdate(LocalDate.of(1985, 5, 21))
                .build();

        guestRepository.saveAll(List.of(guest1, guest2, guest3, guest4, guest5));
        log.info("DataSeeder - seeded guests.");
    }

    private void seedBookings() {
        if (bookingRepository.count() != 0) {
            log.info("DataSeeder - didn't seed any bookings.");
            return;
        }

        Room room1 = roomRepository.findAll().get(0);
        Room room2 = roomRepository.findAll().get(1);
        Room room3 = roomRepository.findAll().get(2);
        Room room4 = roomRepository.findAll().get(3);

        User user1 = userRepository.findAll().get(0);
        User user2 = userRepository.findAll().get(1);
        User user3 = userRepository.findAll().get(2);
        User user4 = userRepository.findAll().get(3);

        Guest guest1 = guestRepository.findAll().get(0);
        Guest guest2 = guestRepository.findAll().get(1);
        Guest guest3 = guestRepository.findAll().get(2);
        Guest guest4 = guestRepository.findAll().get(3);

        Booking booking1 = Booking.builder()
                .room(room1)
                .user(user1)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(3))
                .totalPrice(new BigDecimal("300.00"))
                .guests(Set.of(guest1))
                .build();

        Booking booking2 = Booking.builder()
                .room(room2)
                .user(user2)
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(4))
                .totalPrice(new BigDecimal("400.00"))
                .guests(Set.of(guest2))
                .build();

        Booking booking3 = Booking.builder()
                .room(room3)
                .user(user3)
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(5))
                .totalPrice(new BigDecimal("450.00"))
                .guests(Set.of(guest3))
                .build();

        Booking booking4 = Booking.builder()
                .room(room4)
                .user(user4)
                .startDate(LocalDate.now().plusDays(3))
                .endDate(LocalDate.now().plusDays(6))
                .totalPrice(new BigDecimal("500.00"))
                .guests(Set.of(guest4))
                .build();

        bookingRepository.saveAll(List.of(booking1, booking2, booking3, booking4));
        log.info("DataSeeder - seeded bookings.");
    }
}

