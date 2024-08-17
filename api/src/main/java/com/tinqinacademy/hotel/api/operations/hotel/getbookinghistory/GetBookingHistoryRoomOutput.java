package com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GetBookingHistoryRoomOutput {
    private String roomNumber;

    private BigDecimal roomPrice;

    private Integer roomFloor;
}
