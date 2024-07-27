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

    @Query("""
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
    List<Room> findAvailableRooms(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    Optional<Room> findByRoomNumber(String roomNumber);

    Boolean existsByRoomNumber(String roomNumber);

}