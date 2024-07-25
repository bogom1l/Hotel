package com.tinqinacademy.hotel.persistence.model.operations.system.getroomreport;

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
