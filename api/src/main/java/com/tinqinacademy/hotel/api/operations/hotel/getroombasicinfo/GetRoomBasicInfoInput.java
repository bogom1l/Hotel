package com.tinqinacademy.hotel.api.operations.hotel.getroombasicinfo;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetRoomBasicInfoInput {
    private String roomId;
}
