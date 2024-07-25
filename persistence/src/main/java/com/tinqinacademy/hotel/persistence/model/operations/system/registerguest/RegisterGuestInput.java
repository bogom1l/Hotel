package com.tinqinacademy.hotel.persistence.model.operations.system.registerguest;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterGuestInput {
//    @Valid
//    @NotEmpty(message = "Visitor list should be not empty")
    private List<GuestInput> guests;
}
