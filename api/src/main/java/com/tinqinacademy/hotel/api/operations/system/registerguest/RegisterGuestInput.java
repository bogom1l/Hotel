package com.tinqinacademy.hotel.api.operations.system.registerguest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterGuestInput {
    @Valid
    @NotEmpty(message = "Guests list should not be empty")
    private List<GuestInput> guests;
}
