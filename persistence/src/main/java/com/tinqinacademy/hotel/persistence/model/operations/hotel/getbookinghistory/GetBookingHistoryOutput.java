package com.tinqinacademy.hotel.persistence.model.operations.hotel.getbookinghistory;

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
