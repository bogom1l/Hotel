package com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory;

import com.tinqinacademy.hotel.api.base.OperationInput;
import com.tinqinacademy.hotel.api.base.OperationOutput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetBookingHistoryOutput implements OperationOutput {
    private List<GetBookingHistoryBookingOutput> bookings;
}
