package com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetBookingHistoryOutput {
    private List<GetBookingHistoryBookingOutput> bookings;
}
