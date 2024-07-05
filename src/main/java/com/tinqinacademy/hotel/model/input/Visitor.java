package com.tinqinacademy.hotel.model.input;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Visitor {
    private String startDate;
    private String endDate;
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String idCardNo;
    private String idCardValidity;
    private String idCardIssueAuthority;
    private String idCardIssueDate;
}
