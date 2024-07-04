package com.tinqinacademy.hotel.model;

import com.tinqinacademy.hotel.model.enums.BathroomType;
import com.tinqinacademy.hotel.model.enums.BedSize;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RoomOutput {

    private String id;
    private String roomNumber;
    private Integer bedCount;
    private BedSize bedSize;
    private Integer floor;
    private BigDecimal price;
    private BathroomType bathroomType;

}
