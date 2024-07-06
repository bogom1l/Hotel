package com.tinqinacademy.hotel.model.getroomreport;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RegisterReportOutput {
    private List<VisitorReportOutput> visitors;
}
