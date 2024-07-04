package com.tinqinacademy.hotel.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GetRoom {
    private String bedType;
    private Integer floor;
}
