package com.tinqinacademy.hotel.api.operations.system.updatepartiallyroom;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.api.base.OperationInput;
import com.tinqinacademy.hotel.api.validation.bathroomtype.BathroomTypeValidation;
import com.tinqinacademy.hotel.api.validation.bedsize.BedSizeValidation;
import com.tinqinacademy.hotel.api.validation.room.RoomNumberValidation;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class UpdatePartiallyRoomInput implements OperationInput {
    @JsonIgnore
    private String roomId;

    @BedSizeValidation(optional = true)
    private String bedSize;

    @BathroomTypeValidation(optional = true)
    private String bathroomType;

    @RoomNumberValidation(optional = true)
    private String roomNumber;

    @Positive(message = "Price should be positive")
    private BigDecimal price;
}

