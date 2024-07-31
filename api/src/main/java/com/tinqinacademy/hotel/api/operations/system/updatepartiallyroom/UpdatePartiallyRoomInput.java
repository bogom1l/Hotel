package com.tinqinacademy.hotel.api.operations.system.updatepartiallyroom;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.api.base.OperationInput;
import com.tinqinacademy.hotel.persistence.customvalidation.RoomNumberValidation;
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

    private String bedSize;

    private String bathroomType;

    @RoomNumberValidation(optional = true) // Custom validation
    private String roomNumber;

    @Positive(message = "Price should be positive")
    private BigDecimal price;
}

