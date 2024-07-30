package com.tinqinacademy.hotel.api.operations.system.deleteroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DeleteRoomInput {

    @JsonIgnore
    private String id;
}
