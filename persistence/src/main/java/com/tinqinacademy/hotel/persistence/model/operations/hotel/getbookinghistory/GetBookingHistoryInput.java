package com.tinqinacademy.hotel.persistence.model.operations.hotel.getbookinghistory;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetBookingHistoryInput {
    private String phoneNumber;
}
