package com.tinqinacademy.hotel.model.operations.getrooms;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GetRoomInput {
    private String id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer bedCount;
    private String bedSize; // TODO: enum?
    private String bathroomType; // TODO: enum?
}
