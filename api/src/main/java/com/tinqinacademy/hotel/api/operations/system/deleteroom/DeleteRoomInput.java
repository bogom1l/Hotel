package com.tinqinacademy.hotel.api.operations.system.deleteroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.api.base.OperationInput;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DeleteRoomInput implements OperationInput {
    @JsonIgnore
    private String id;
}
