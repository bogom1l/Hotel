package com.tinqinacademy.hotel.persistence.repository;

import com.tinqinacademy.hotel.persistence.model.Bed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BedRepository extends JpaRepository<Bed, UUID> {

    @Query(value = """
            SELECT b.* FROM beds b JOIN rooms_beds rb ON b.id = rb.beds_id WHERE rb.room_id = :roomId
            """, nativeQuery = true)
    List<Bed> findAllByRoomId(@Param("roomId") UUID roomId);
}
