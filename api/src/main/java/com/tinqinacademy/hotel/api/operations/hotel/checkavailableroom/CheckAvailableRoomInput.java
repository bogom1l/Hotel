package com.tinqinacademy.hotel.api.operations.hotel.checkavailableroom;

import com.tinqinacademy.hotel.api.base.OperationInput;
import com.tinqinacademy.hotel.api.validation.bathroomtype.BathroomTypeValidation;
import com.tinqinacademy.hotel.api.validation.bedsize.BedSizeValidation;
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

    @BedSizeValidation
    private String bedSize;

    @BathroomTypeValidation
    private String bathroomType;
}
