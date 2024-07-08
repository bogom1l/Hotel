package com.tinqinacademy.hotel.model.operations.getroominfo;

import com.tinqinacademy.hotel.model.enums.BathroomType;
import com.tinqinacademy.hotel.model.enums.BedSize;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RoomInfoOutput {
    private String id;
    private BigDecimal price;
    private Integer floor;
    private BedSize bedSize;
    private BathroomType bathroomType;
    private Integer bedCount;
    private List<LocalDate> datesOccupied;
}
