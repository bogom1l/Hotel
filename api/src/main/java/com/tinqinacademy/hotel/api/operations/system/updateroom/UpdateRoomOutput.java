package com.tinqinacademy.hotel.api.operations.system.updateroom;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UpdateRoomOutput implements OperationOutput {
    private UUID id;
}
