package com.tinqinacademy.hotel.model.getrooms;

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
    private String bedSize;
    private String bathroomType;
}
