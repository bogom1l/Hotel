package com.tinqinacademy.hotel.api.operations.system.updatepartiallyroom;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UpdatePartiallyRoomOutput implements OperationOutput {
    private UUID id;
}

