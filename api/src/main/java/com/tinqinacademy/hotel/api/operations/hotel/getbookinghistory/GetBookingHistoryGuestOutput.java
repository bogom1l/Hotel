package com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GetBookingHistoryGuestOutput {
    private String firstName;

    private String lastName;

    private String phoneNumber;

    private LocalDate birthdate;
}
