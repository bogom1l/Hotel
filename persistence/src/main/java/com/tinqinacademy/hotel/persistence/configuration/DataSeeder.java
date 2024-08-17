package com.tinqinacademy.hotel.persistence.configuration;

import com.tinqinacademy.hotel.persistence.model.Bed;
import com.tinqinacademy.hotel.persistence.model.Booking;
import com.tinqinacademy.hotel.persistence.model.Guest;
import com.tinqinacademy.hotel.persistence.model.Room;
import com.tinqinacademy.hotel.persistence.model.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.model.enums.BedSize;
import com.tinqinacademy.hotel.persistence.repository.BedRepository;
import com.tinqinacademy.hotel.persistence.repository.BookingRepository;
import com.tinqinacademy.hotel.persistence.repository.GuestRepository;
import com.tinqinacademy.hotel.persistence.repository.RoomRepository;
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
import java.util.UUID;

/**
 * This class is responsible for seeding initial data into the database when the application starts.
 */
@Slf4j
@Component
@Order(1)
public class DataSeeder implements ApplicationRunner {
    private final BedRepository bedRepository;
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public DataSeeder(BedRepository bedRepository, RoomRepository roomRepository, GuestRepository guestRepository, BookingRepository bookingRepository) {
        this.bedRepository = bedRepository;
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        seedBeds();
        seedRooms();
        seedGuests();
        seedBookings();
    }

    private void seedBeds() {
        if (bedRepository.count() != 0) {
            log.info("DataSeeder - Beds were not seeded because they already exist.");
            return;
        }

        Bed singleBed = Bed.builder()
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
        log.info("DataSeeder - Sample beds seeded successfully.");
    }

    private void seedRooms() {
        if (roomRepository.count() != 0) {
            log.info("DataSeeder - Rooms were not seeded because they already exist.");
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
        log.info("DataSeeder - Sample rooms seeded successfully.");
    }

    private void seedGuests() {
        if (guestRepository.count() != 0) {
            log.info("DataSeeder - Guests were not seeded because they already exist.");
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
        log.info("DataSeeder - Sample guests seeded successfully.");
    }

    private void seedBookings() {
        if (bookingRepository.count() != 0) {
            log.info("DataSeeder - Bookings were not seeded because they already exist.");
            return;
        }

        Room room1 = roomRepository.findAll().get(0);
        Room room2 = roomRepository.findAll().get(1);
        Room room3 = roomRepository.findAll().get(2);
        Room room4 = roomRepository.findAll().get(3);

        Guest guest1 = guestRepository.findAll().get(0);
        Guest guest2 = guestRepository.findAll().get(1);
        Guest guest3 = guestRepository.findAll().get(2);
        Guest guest4 = guestRepository.findAll().get(3);

        UUID userId = UUID.randomUUID();

        Booking booking1 = Booking.builder()
                .room(room1)
                .userId(userId)
                .startDate(LocalDate.now().minusWeeks(5))
                .endDate(LocalDate.now().minusWeeks(4))
                .totalPrice(new BigDecimal("300.00"))
                .guests(Set.of(guest1))
                .build();

        Booking booking2 = Booking.builder()
                .room(room2)
                .userId(userId)
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(4))
                .totalPrice(new BigDecimal("400.00"))
                .guests(Set.of(guest2))
                .build();

        Booking booking3 = Booking.builder()
                .room(room3)
                .userId(userId)
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(5))
                .totalPrice(new BigDecimal("450.00"))
                .guests(Set.of(guest3))
                .build();

        Booking booking4 = Booking.builder()
                .room(room4)
                .userId(userId)
                .startDate(LocalDate.now().plusDays(15))
                .endDate(LocalDate.now().plusDays(25))
                .totalPrice(new BigDecimal("500.00"))
                .guests(Set.of(guest4))
                .build();

        bookingRepository.saveAll(List.of(booking1, booking2, booking3, booking4));
        log.info("DataSeeder - Sample bookings seeded successfully.");
    }
}

