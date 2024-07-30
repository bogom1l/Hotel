package com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetBookingHistoryUserOutput {
    private String userEmail;
    private String userPhoneNumber;
}
