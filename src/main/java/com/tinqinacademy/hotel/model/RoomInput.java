package com.tinqinacademy.hotel.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RoomInput {

    private String id;
    private String roomNumber;
    private Integer bedCount;
    private String bedSize;
    private Integer floor;
    private BigDecimal price;
    private String bathroomType;

}
