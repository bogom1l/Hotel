package com.tinqinacademy.hotel.api.operations.getrooms;

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
