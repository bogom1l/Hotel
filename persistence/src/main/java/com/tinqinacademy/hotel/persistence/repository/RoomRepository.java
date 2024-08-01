package com.tinqinacademy.hotel.persistence.repository;

import com.tinqinacademy.hotel.persistence.model.Room;
import com.tinqinacademy.hotel.persistence.model.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.model.enums.BedSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    Optional<Room> findByRoomNumber(String roomNumber);

    Boolean existsByRoomNumber(String roomNumber);

    @Query(value = """
             SELECT DISTINCT r FROM Room r JOIN r.beds b
             WHERE b.bedSize = :bedSize AND r.bathroomType = :bathroomType
            """)
    List<Room> findRoomsByBedSizeAndBathroomType(
            @Param("bedSize") BedSize bedSize,
            @Param("bathroomType") BathroomType bathroomType);

    @Query("""
            SELECT r FROM Room r LEFT JOIN FETCH r.beds WHERE r.id = :roomId
            """)
    Optional<Room> findByIdWithBeds(@Param("roomId") UUID roomId);

    @Query(value = """
            SELECT r.*
            FROM rooms r
            LEFT JOIN bookings b ON r.id = b.room_id
                 AND b.start_date <= :endDate
                 AND b.end_date >= :startDate
            WHERE b.room_id IS NULL;
            """, nativeQuery = true)
    Optional<List<Room>> findAvailableRoomsBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = """
            SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END
            FROM rooms r
            LEFT JOIN bookings b ON r.id = b.room_id
                 AND b.start_date <= :endDate
                 AND b.end_date >= :startDate
            WHERE r.id = :roomId AND b.room_id IS NULL;
            """, nativeQuery = true)
    Boolean isRoomAvailableByRoomIdAndBetweenDates(@Param("roomId") UUID roomId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);



    @Query(value = """
        SELECT r.*
        FROM rooms r
        LEFT JOIN bookings b ON r.id = b.room_id
             AND (:startDate IS NULL OR b.start_date <= :endDate)
             AND (:endDate IS NULL OR b.end_date >= :startDate)
        LEFT JOIN rooms_beds rb ON r.id = rb.room_id
        LEFT JOIN beds bd ON rb.beds_id = bd.id
        WHERE (:startDate IS NULL OR :endDate IS NULL OR b.room_id IS NULL)
          AND (:bedSize IS NULL OR bd.bed_size = :bedSize)
          AND (:bathroomType IS NULL OR r.bathroom_type = :bathroomType);
        """, nativeQuery = true)
    Optional<List<Room>> findAvailableRooms(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("bedSize") BedSize bedSize,
            @Param("bathroomType") BathroomType bathroomType);
}