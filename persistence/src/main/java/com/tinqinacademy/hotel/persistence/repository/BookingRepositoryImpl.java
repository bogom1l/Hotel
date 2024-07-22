package com.tinqinacademy.hotel.persistence.repository;

import com.tinqinacademy.hotel.persistence.model.Booking;
import com.tinqinacademy.hotel.persistence.model.Guest;
import com.tinqinacademy.hotel.persistence.repository.contracts.BookingRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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

        return findById(booking.getId()).orElse(null);
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
        String query = "SELECT * FROM bookings WHERE id = ?";
        return jdbcTemplate.query(query, new Object[]{id}, rs -> {
            if (rs.next()) {
                return Optional.of(bookingRowMapper(rs));
            } else {
                return Optional.empty();
            }
        });
    }

    private Booking bookingRowMapper(ResultSet rs) throws SQLException {
        UUID bookingId = UUID.fromString(rs.getString("id"));
        return Booking.builder()
                .id(bookingId)
                .roomId(UUID.fromString(rs.getString("room_id")))
                .userId(UUID.fromString(rs.getString("user_id")))
                .startDate(rs.getDate("start_date").toLocalDate())
                .endDate(rs.getDate("end_date").toLocalDate())
                .totalPrice(rs.getBigDecimal("total_price"))
                .guests(fetchGuestsForBooking(bookingId))
                .build();
    }

    private Set<Guest> fetchGuestsForBooking(UUID bookingId) {
        String query = "SELECT g.* FROM guests g JOIN bookings_guests bg ON g.id = bg.guest_id WHERE bg.booking_id = ?";
        return new HashSet<>(jdbcTemplate.query(query, new Object[]{bookingId}, guestRowMapper()));
    }

    private RowMapper<Guest> guestRowMapper() {
        return (rs, rowNum) -> Guest.builder()
                .id(UUID.fromString(rs.getString("id")))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .phoneNumber(rs.getString("phone_number"))
                .idCardNumber(rs.getString("id_card_number"))
                .idCardValidity(rs.getDate("id_card_validity").toLocalDate())
                .idCardIssueAuthority(rs.getString("id_card_issue_authority"))
                .idCardIssueDate(rs.getDate("id_card_issue_date").toLocalDate())
                .birthdate(rs.getDate("birthdate").toLocalDate())
                .build();
    }

    @Override
    public Booking update(Booking booking) {


        return findById(booking.getId()).orElse(null);
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
