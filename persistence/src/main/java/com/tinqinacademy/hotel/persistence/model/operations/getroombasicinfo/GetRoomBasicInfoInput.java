package com.tinqinacademy.hotel.persistence.model.operations.getroombasicinfo;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetRoomBasicInfoInput {
    private String roomId;
}
