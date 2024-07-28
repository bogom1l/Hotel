package com.tinqinacademy.hotel.persistence.model.operations.hotel.getroombasicinfo;

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
