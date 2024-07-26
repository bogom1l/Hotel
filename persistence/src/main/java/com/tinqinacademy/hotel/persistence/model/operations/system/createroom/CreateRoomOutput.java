package com.tinqinacademy.hotel.persistence.model.operations.system.createroom;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateRoomOutput {
    private UUID id;
}
