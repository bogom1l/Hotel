package com.tinqinacademy.hotel.api.operations.system.updateroom;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.api.base.OperationInput;
import com.tinqinacademy.hotel.persistence.customvalidation.RoomNumberValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class UpdateRoomInput implements OperationInput {
    @JsonIgnore
    private String roomId;

    @NotBlank(message = "Bed size is mandatory")
    private String bedSize;

    @NotBlank(message = "Bathroom type is mandatory")
    private String bathroomType;

    @RoomNumberValidation // Custom validation
    @NotBlank(message = "Room number is mandatory")
    private String roomNumber;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price should be positive")
    private BigDecimal price;
}
