package com.tinqinacademy.hotel.api.operations.hotel.checkavailableroom;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CheckAvailableRoomOutput implements OperationOutput {
    private List<String> ids;
}