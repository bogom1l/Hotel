package com.tinqinacademy.hotel.api.operations.hotel.checkavailableroom;

import com.tinqinacademy.hotel.api.base.OperationInput;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CheckAvailableRoomInput implements OperationInput {
    private LocalDate startDate;

    private LocalDate endDate;

    private String bedSize;

    private String bathroomType;
}
