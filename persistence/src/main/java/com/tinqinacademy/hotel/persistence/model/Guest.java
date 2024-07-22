package com.tinqinacademy.hotel.persistence.model;

import com.tinqinacademy.hotel.persistence.model.contracts.Entity;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Guest implements Entity {
    private UUID id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String idCardNumber;
    private LocalDate idCardValidity;
    private String idCardIssueAuthority;
    private LocalDate idCardIssueDate;
    private LocalDate birthdate;
}
