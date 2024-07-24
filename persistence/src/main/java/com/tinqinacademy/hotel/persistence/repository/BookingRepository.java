package com.tinqinacademy.hotel.persistence.repository;

import com.tinqinacademy.hotel.persistence.model.Booking;
import com.tinqinacademy.hotel.persistence.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Booking b " +
            "WHERE b.room = :room " +
            "AND b.startDate <= :endDate " +
            "AND b.endDate >= :startDate")
    boolean existsByRoomAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            @Param("room") Room room,
            @Param("endDate") LocalDate endDate,
            @Param("startDate") LocalDate startDate);

    Optional<List<Booking>> findAllByRoomId(UUID roomId);
}
