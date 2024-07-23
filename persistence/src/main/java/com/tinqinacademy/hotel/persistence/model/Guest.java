package com.tinqinacademy.hotel.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "guests")
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "id_card_number", nullable = false)
    private String idCardNumber;

    @Column(name = "id_card_validity", nullable = false)
    private LocalDate idCardValidity;

    @Column(name = "id_card_issue_authority", nullable = false)
    private String idCardIssueAuthority;

    @Column(name = "id_card_issue_date", nullable = false)
    private LocalDate idCardIssueDate;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

