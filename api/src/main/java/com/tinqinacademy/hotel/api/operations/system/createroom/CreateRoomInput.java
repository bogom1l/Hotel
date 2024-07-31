package com.tinqinacademy.hotel.api.operations.system.createroom;

import com.tinqinacademy.hotel.api.base.OperationInput;
import com.tinqinacademy.hotel.persistence.customvalidation.RoomNumberValidation;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateRoomInput implements OperationInput {
    @NotBlank(message = "Bed size is mandatory")
    private String bedSize;

    @NotBlank(message = "Bathroom type is mandatory")
    private String bathroomType;

    @NotNull(message = "Floor is mandatory")
    @Min(value = 0, message = "Floor should be at least 0")
    @Max(value = 15, message = "Floor should be maximum 15")
    private Integer floor;

    @RoomNumberValidation // Custom validation
    @NotBlank(message = "Room number is mandatory")
    private String roomNumber;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price should be positive")
    private BigDecimal price;
}
