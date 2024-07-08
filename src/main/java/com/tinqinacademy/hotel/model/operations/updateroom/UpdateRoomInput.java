package com.tinqinacademy.hotel.model.operations.updateroom;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class UpdateRoomInput {
    private String roomId;
    private Integer bedCount;
    private String bedSize;
    private String bathroomType;
    private Integer floor;
    private String roomNo;
    private BigDecimal price;
}
