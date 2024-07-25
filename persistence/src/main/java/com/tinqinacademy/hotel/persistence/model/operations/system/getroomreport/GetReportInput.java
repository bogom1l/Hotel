package com.tinqinacademy.hotel.persistence.model.operations.system.getroomreport;

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
    private String roomNo;

    //guest?
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String idCardNo;
    private String idCardValidity;
    private String idCardIssueAuthority;
    private String idCardIssueDate;
}
