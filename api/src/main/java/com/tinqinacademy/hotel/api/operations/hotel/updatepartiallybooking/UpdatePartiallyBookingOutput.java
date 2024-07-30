package com.tinqinacademy.hotel.api.operations.hotel.updatepartiallybooking;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UpdatePartiallyBookingOutput implements OperationOutput {
    private UUID id;
}
