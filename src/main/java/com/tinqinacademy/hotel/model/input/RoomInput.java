package com.tinqinacademy.hotel.model.input;

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
    private Integer floor;
    private BigDecimal price;
    private String bedSize;
    private String bathroomType;

}
