package com.tinqinacademy.hotel.persistence.model.operations.hotel.getroombasicinfo;

import com.tinqinacademy.hotel.persistence.model.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.model.enums.BedSize;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetRoomBasicInfoOutput {
    private UUID id;
    private BigDecimal price;
    private Integer floor;
    private BedSize bedSize;
    private BathroomType bathroomType;
    private List<LocalDate> datesOccupied;

    private String roomNumber;
}
