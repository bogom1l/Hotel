package com.tinqinacademy.hotel.api.operations.system.getallusers;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOutput {
    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private LocalDate birthdate;
}
