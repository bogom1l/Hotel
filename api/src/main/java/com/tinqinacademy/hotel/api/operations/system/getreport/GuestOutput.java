package com.tinqinacademy.hotel.api.operations.system.getreport;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GuestOutput {
    private LocalDate startDate;

    private LocalDate endDate;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String idCardNumber;

    private LocalDate idCardValidity;

    private String idCardIssueAuthority;

    private LocalDate idCardIssueDate;
}
