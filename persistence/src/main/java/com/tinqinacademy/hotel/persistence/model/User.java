package com.tinqinacademy.hotel.persistence.model;

import com.tinqinacademy.hotel.persistence.model.contracts.Entity;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements Entity {

    private UUID id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthdate;

}
