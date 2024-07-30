package com.tinqinacademy.hotel.api.operations.hotel.unbookroom;

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
