package com.tinqinacademy.hotel.api.operations.system.getreport;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GetReportOutput {
    private List<GuestOutput> guests;
}
