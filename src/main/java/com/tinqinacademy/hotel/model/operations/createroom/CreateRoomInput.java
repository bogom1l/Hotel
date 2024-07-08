package com.tinqinacademy.hotel.model.operations.createroom;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CreateRoomInput {
    private Integer bedCount;
    private String bedSize; // Enum BedSize
    private String bathroomType; // Enum BathRoomType
    private Integer floor;
    private String roomNo;
    private BigDecimal price;
}
