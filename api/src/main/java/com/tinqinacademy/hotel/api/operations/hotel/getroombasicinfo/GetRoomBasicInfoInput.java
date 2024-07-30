package com.tinqinacademy.hotel.api.operations.hotel.getroombasicinfo;

import com.tinqinacademy.hotel.api.base.OperationInput;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetRoomBasicInfoInput implements OperationInput {
    private String roomId;
}
