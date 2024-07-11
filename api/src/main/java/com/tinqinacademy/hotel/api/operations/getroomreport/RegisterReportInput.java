package com.tinqinacademy.hotel.api.operations.getroomreport;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RegisterReportInput {
    private String startDate;
    private String endDate;
    private String fistName;
    private String lastName;
    private String phoneNo;
    private String idCardNo;
    private String idCardValidity;
    private String idCardIssueAuthority;
    private String idCardIssueDate;
    private String roomNo;
}
