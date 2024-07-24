package com.tinqinacademy.hotel.persistence.model.operations.checkavailableroom;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CheckAvailableRoomOutput {
    private List<String> ids;
}
