package com.tinqinacademy.hotel.persistence.models;

import com.tinqinacademy.hotel.persistence.models.enums.BedSize;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Bed {
    private UUID id;
    private BedSize bedSize;
    private Integer capacity;
}
