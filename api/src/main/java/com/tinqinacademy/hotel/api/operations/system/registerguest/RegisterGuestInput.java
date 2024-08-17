package com.tinqinacademy.hotel.api.operations.system.registerguest;

import com.tinqinacademy.hotel.api.base.OperationInput;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterGuestInput implements OperationInput {
    @NotBlank(message = "Room id is mandatory")
    private String roomId;

    @NotEmpty(message = "Guests list should not be empty")
    private List<@Valid GuestInput> guests;
}
