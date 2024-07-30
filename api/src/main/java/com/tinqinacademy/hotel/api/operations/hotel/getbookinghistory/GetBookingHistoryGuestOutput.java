package com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetBookingHistoryGuestOutput {

    private String firstName;

    private String lastName;

    private String phoneNumber;
}
