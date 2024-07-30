package com.tinqinacademy.hotel.api.operations.hotel.checkavailableroom;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CheckAvailableRoomInput {
    private LocalDate startDate;
    private LocalDate endDate;
    private String bedSize;
    private String bathroomType;
}
