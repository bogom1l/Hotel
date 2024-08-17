package com.tinqinacademy.hotel.persistence.repository;

import com.tinqinacademy.hotel.persistence.model.Booking;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    List<Booking> findAllByUserId(UUID userId);

    List<Booking> findAllByRoomId(UUID roomId);

    Optional<Booking> findByRoomIdAndStartDateAndEndDate(UUID roomId, LocalDate startDate, LocalDate endDate);

    @Query("""
                     SELECT b FROM Booking b 
                     WHERE (b.startDate BETWEEN :startDate AND :endDate) 
                     OR (b.endDate BETWEEN :startDate AND :endDate)
            """)
    List<Booking> findByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("""
                     SELECT b FROM Booking b
                     JOIN b.guests g
                     WHERE g.idCardNumber = :idCardNumber
            """)
    List<Booking> findByGuestIdCardNumber(@Param("idCardNumber") String idCardNumber);

    @Modifying
    @Transactional
    @Query("DELETE FROM Booking b WHERE b.room.id = :roomId")
    void deleteBookingsByRoomId(@Param("roomId") UUID roomId);

    @Modifying
    @Transactional
    @Query(value = """
            DELETE FROM bookings_guests
             WHERE booking_id = :bookingId
            """, nativeQuery = true)
    void deleteFromBookingsGuestsByBookingId(@Param("bookingId") UUID bookingId);
}
