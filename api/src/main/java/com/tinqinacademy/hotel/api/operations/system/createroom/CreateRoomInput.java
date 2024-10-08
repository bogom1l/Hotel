package com.tinqinacademy.hotel.api.operations.system.createroom;

import com.tinqinacademy.hotel.api.base.OperationInput;
import com.tinqinacademy.hotel.api.validation.bathroomtype.BathroomTypeValidation;
import com.tinqinacademy.hotel.api.validation.bedsize.BedSizeValidation;
import com.tinqinacademy.hotel.api.validation.room.RoomNumberValidation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateRoomInput implements OperationInput {
    @BedSizeValidation
    private String bedSize;

    @BathroomTypeValidation
    private String bathroomType;

    @NotNull(message = "Floor is mandatory")
    @Min(value = 0, message = "Floor should be at least 0")
    @Max(value = 15, message = "Floor should be maximum 15")
    private Integer floor;

    @RoomNumberValidation
    private String roomNumber;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price should be positive")
    private BigDecimal price;
}
