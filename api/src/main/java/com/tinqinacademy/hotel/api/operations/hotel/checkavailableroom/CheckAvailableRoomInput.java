package com.tinqinacademy.hotel.api.operations.hotel.checkavailableroom;

import com.tinqinacademy.hotel.api.base.OperationInput;
import com.tinqinacademy.hotel.api.validation.bathroomtype.BathroomTypeValidation;
import com.tinqinacademy.hotel.api.validation.bedsize.BedSizeValidation;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CheckAvailableRoomInput implements OperationInput {
    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "Start date is required")
    private LocalDate endDate;

    @BedSizeValidation(optional = true)
    private String bedSize;

    @BathroomTypeValidation(optional = true)
    private String bathroomType;
}
