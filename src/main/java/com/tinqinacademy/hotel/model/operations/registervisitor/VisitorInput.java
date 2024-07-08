package com.tinqinacademy.hotel.model.operations.registervisitor;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class VisitorInput {
    private LocalDate startDate;
    private LocalDate endDate;
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String idCardNo;
    private String idCardValidity; // TODO: LocalDate
    private String idCardIssueAuthority;
    private String idCardIssueDate; // TODO: LocalDate
}
