package com.tinqinacademy.hotel.api.operations.system.getreport;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GetReportOutput implements OperationOutput {
    private List<GuestOutput> guests;
}
