package com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GetBookingHistoryOutput implements OperationOutput {
    private List<GetBookingHistoryBookingOutput> bookings;
}
