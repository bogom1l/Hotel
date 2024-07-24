package com.tinqinacademy.hotel.persistence.model.checkavailableroom;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CheckAvailableRoomInput {
    private String id;
    private LocalDate startDate;
    private LocalDate endDate;
    // private Integer bedCount;
    private String bedSize;
    private String bathroomType;
}
