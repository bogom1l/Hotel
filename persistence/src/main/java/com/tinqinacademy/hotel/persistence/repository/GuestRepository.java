package com.tinqinacademy.hotel.persistence.repository;

import com.tinqinacademy.hotel.persistence.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GuestRepository extends JpaRepository<Guest, UUID> {
    @Query(value = "SELECT * FROM guests g " +
            "WHERE (:firstName IS NULL OR LOWER(g.first_name) = LOWER(:firstName)) " +
            "AND (:lastName IS NULL OR LOWER(g.last_name) = LOWER(:lastName)) " +
            "AND (:phoneNo IS NULL OR g.phone_number = :phoneNo) " +
            "AND (:idCardNo IS NULL OR g.id_card_number = :idCardNo) " +
            "AND (:idCardValidity IS NULL OR g.id_card_validity = CAST(:idCardValidity AS DATE)) " +
            "AND (:idCardIssueAuthority IS NULL OR LOWER(g.id_card_issue_authority) = LOWER(:idCardIssueAuthority)) " +
            "AND (:idCardIssueDate IS NULL OR g.id_card_issue_date = CAST(:idCardIssueDate AS DATE))",
            nativeQuery = true)
    List<Guest> findMatchingGuests(@Param("firstName") String firstName,
                                   @Param("lastName") String lastName,
                                   @Param("phoneNo") String phoneNo,
                                   @Param("idCardNo") String idCardNo,
                                   @Param("idCardValidity") String idCardValidity, // Handle as String and cast in SQL
                                   @Param("idCardIssueAuthority") String idCardIssueAuthority,
                                   @Param("idCardIssueDate") String idCardIssueDate); // Handle as String and cast in SQL

    @Query(value = """
            SELECT g.* FROM guests g JOIN bookings_guests bg ON g.id = bg.guests_id WHERE bg.booking_id = :bookingId
            """, nativeQuery = true)
    List<Guest> findAllByBookingId(@Param("bookingId") UUID bookingId);
}