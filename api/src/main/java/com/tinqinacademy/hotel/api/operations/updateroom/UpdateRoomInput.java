package com.tinqinacademy.hotel.api.operations.updateroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.persistence.customvalidation.RoomNumberValidation;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class UpdateRoomInput {

    @JsonIgnore
    private String roomId;

    @NotNull(message = "Bed count is mandatory")
    @Min(value = 1, message = "Bed count should be at least 1")
    @Max(value = 4, message = "Bed count should be maximum 4")
    private Integer bedCount;

    @NotBlank(message = "Bed size is mandatory")
    private String bedSize;

    @NotBlank(message = "Bathroom type is mandatory")
    private String bathroomType;

    @NotNull(message = "Floor is mandatory")
    @Min(value = 1, message = "Floor should be at least 1")
    @Max(value = 15, message = "Floor should be maximum 15")
    private Integer floor;

    @NotBlank(message = "Room number is mandatory")
    @RoomNumberValidation // Custom validation
    private String roomNo;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price should be positive")
    private BigDecimal price;
}
