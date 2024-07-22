package com.tinqinacademy.hotel.persistence.repository;

import com.tinqinacademy.hotel.persistence.model.Booking;
import com.tinqinacademy.hotel.persistence.model.Guest;
import com.tinqinacademy.hotel.persistence.repository.contracts.BookingRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class BookingRepositoryImpl implements BookingRepository {

    private final JdbcTemplate jdbcTemplate;

    public BookingRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Booking save(Booking booking) {
        String query = "INSERT into bookings (id, room_id, user_id, start_date, end_date, total_price) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(query, booking.getId(), booking.getRoomId(), booking.getUserId(),
                booking.getStartDate(), booking.getEndDate(),booking.getTotalPrice());

        if (booking.getGuests() != null) {
            for (Guest guest : booking.getGuests()) {
                if (!guestExists(guest.getId())) {
                    saveGuest(guest); // add the guest in the guest table
                }
                saveInBookingGuests(booking.getId(), guest.getId()); // save in the bookings_guests table
            }
        }

        return booking; // it doesn't return the newly created booking, it only returns the input booking.
                        // and this problem is in all repositories SAVE methods. TODO
    }

    private boolean guestExists(UUID guestId) {
        String query = "SELECT COUNT(*) FROM guests WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(query, new Object[]{guestId}, Integer.class);
        return count != null && count > 0;
    }

    private void saveGuest(Guest guest) {
        String query = "INSERT INTO guests (id, first_name, last_name, phone_number, id_card_number, id_card_validity, id_card_issue_authority, id_card_issue_date, birthdate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(query, guest.getId(), guest.getFirstName(), guest.getLastName(),
                guest.getPhoneNumber(), guest.getIdCardNumber(), guest.getIdCardValidity(),
                guest.getIdCardIssueAuthority(), guest.getIdCardIssueDate(), guest.getBirthdate());
    }

    private void saveInBookingGuests(UUID bookingId, UUID guestId) {
        String query = "INSERT INTO bookings_guests (booking_id, guest_id) VALUES (?, ?)";
        jdbcTemplate.update(query, bookingId, guestId);
    }

    @Override
    public Optional<Booking> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Booking update(Booking entity) {
        return null;
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public List<Booking> findAll() {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteAll() {

    }
}
