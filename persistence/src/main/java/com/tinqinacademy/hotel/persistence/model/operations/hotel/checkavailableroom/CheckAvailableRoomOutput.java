package com.tinqinacademy.hotel.persistence.model.operations.hotel.checkavailableroom;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CheckAvailableRoomOutput {
    private List<String> ids;
}
