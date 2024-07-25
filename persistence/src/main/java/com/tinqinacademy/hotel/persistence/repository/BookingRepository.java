package com.tinqinacademy.hotel.persistence.repository;

import com.tinqinacademy.hotel.persistence.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {

    Optional<List<Booking>> findAllByRoomId(UUID roomId);

    Optional<Booking> findByRoomIdAndStartDateAndEndDate(UUID id, LocalDate startDate, LocalDate endDate);

    @Query("""
                     SELECT b FROM Booking b 
                     WHERE b.room.id = :roomId 
                     AND ((b.startDate BETWEEN :startDate AND :endDate) 
                     OR (b.endDate BETWEEN :startDate AND :endDate))
            """)
    Optional<List<Booking>> findByRoomIdAndDateRange(@Param("roomId") UUID roomId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


}
