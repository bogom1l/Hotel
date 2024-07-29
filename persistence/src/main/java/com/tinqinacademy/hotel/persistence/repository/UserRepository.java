package com.tinqinacademy.hotel.persistence.repository;

import com.tinqinacademy.hotel.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByPhoneNumberAndFirstNameAndLastName(String phoneNumber, String firstName, String lastName);

    Optional<User> findByPhoneNumber(String phoneNumber);

    @Query(value = """
        SELECT * 
        FROM users 
        WHERE LOWER(first_name) LIKE LOWER(CONCAT('%', :partialName, '%')) 
           OR LOWER(last_name) LIKE LOWER(CONCAT('%', :partialName, '%'))
        """, nativeQuery = true)
    Optional<List<User>> findUsersByPartialName(@Param("partialName") String partialName);

    // Optional<List<User>> findByFirstNameContainingOrLastNameContaining(String partialName, String partialName1);

    /*
    // CREATE EXTENSION fuzzystrmatch;
    // DROP EXTENSION fuzzystrmatch;
    @Query(value = """
        SELECT *
        FROM users
        WHERE SOUNDEX(first_name) = SOUNDEX(:partialName)
           OR SOUNDEX(last_name) = SOUNDEX(:partialName)
        """, nativeQuery = true)
    Optional<List<User>> findUsersBySimilarName(@Param("partialName") String partialName);
     */

}
