package com.tinqinacademy.hotel.persistence.model.operations.system.getreport;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GetReportInput {
    //booking?
    private String startDate;
    private String endDate;

    //guest?
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String idCardNumber;
    private String idCardValidity;
    private String idCardIssueAuthority;
    private String idCardIssueDate;

    //room?
    private String roomNumber;
}
