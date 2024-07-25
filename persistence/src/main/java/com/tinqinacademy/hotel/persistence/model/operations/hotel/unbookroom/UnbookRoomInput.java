package com.tinqinacademy.hotel.persistence.model.operations.hotel.unbookroom;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UnbookRoomInput {
    private String bookingId;
}
