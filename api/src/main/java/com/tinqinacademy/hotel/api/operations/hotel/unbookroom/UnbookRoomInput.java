package com.tinqinacademy.hotel.api.operations.hotel.unbookroom;

import com.tinqinacademy.hotel.api.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
public class UnbookRoomInput implements OperationInput {
    private String bookingId;

    @NotBlank(message = "User id is mandatory")
    private String userId;
}
