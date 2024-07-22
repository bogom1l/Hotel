package com.tinqinacademy.hotel.persistence.model;

import com.tinqinacademy.hotel.persistence.model.contracts.Entity;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Booking implements Entity {
    private UUID id;
    private UUID roomId;
    private UUID userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalPrice;
    private Set<Guest> guests;
}
