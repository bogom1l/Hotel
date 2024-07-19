package com.tinqinacademy.hotel.persistence.model;

import com.tinqinacademy.hotel.persistence.model.contracts.Entity;
import com.tinqinacademy.hotel.persistence.model.enums.BathroomType;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Room implements Entity {
    private UUID id;
    private BigDecimal price;
    private Integer floor;
    private String roomNumber;
    private BathroomType bathroomType;
    private List<Bed> beds; // TODO?: = new empty List
}
