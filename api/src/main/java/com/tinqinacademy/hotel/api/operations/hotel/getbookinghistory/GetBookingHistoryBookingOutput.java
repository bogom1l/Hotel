package com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GetBookingHistoryBookingOutput {
    private GetBookingHistoryRoomOutput room;

    private LocalDate bookingStartDate;

    private LocalDate bookingEndDate;

    private BigDecimal bookingTotalPrice;

    private Set<GetBookingHistoryGuestOutput> guests;
}
