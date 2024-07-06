package com.tinqinacademy.hotel.model.getrooms;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GetRoomOutput {
    private List<String> ids;
}
