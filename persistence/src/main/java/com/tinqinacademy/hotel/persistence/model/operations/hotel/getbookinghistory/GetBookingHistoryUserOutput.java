package com.tinqinacademy.hotel.persistence.model.operations.hotel.getbookinghistory;

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
