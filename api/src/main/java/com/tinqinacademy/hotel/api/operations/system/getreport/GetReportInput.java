package com.tinqinacademy.hotel.api.operations.system.getreport;

import com.tinqinacademy.hotel.api.base.OperationInput;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GetReportInput implements OperationInput {
    //booking
    private String startDate;

    private String endDate;

    //guest
    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String idCardNumber;

    private String idCardValidity;

    private String idCardIssueAuthority;

    private String idCardIssueDate;

    //room
    private String roomNumber;
}
