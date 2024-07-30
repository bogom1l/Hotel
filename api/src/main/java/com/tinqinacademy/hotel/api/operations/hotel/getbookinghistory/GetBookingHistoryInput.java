package com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory;

import com.tinqinacademy.hotel.api.base.OperationInput;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetBookingHistoryInput implements OperationInput {
    private String phoneNumber;
}
