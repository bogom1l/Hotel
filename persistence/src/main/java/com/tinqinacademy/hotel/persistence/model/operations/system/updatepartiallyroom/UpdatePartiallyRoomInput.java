package com.tinqinacademy.hotel.persistence.model.operations.system.updatepartiallyroom;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class UpdatePartiallyRoomInput {

    @JsonIgnore
    private String roomId;

    private String bedSize;

    private String bathroomType;

    // @RoomNumberValidation // Custom validation
    private String roomNumber;

    @Positive(message = "Price should be positive")
    private BigDecimal price;
}

