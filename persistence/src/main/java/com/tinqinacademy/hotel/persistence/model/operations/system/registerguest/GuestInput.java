package com.tinqinacademy.hotel.persistence.model.operations.system.registerguest;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GuestInput {

    private LocalDate startDate;

    private LocalDate endDate;

    private String firstName;

    private String lastName;

    private String phoneNo;

    private String idCardNo;

    private LocalDate idCardValidity;

    private String idCardIssueAuthority;

    private LocalDate idCardIssueDate;

    private String roomId;

    private LocalDate birthdate;

}
