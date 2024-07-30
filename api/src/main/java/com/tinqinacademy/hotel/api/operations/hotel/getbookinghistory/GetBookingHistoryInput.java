package com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetBookingHistoryInput {
    private String phoneNumber;
}
