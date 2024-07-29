package com.tinqinacademy.hotel.persistence.model.operations.hotel.getbookinghistory;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetBookingHistoryRoomOutput {

    private String roomNumber;

    private BigDecimal roomPrice;

    private Integer roomFloor;
}
