package com.tinqinacademy.hotel.persistence.model.getroombasicinfo;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetRoomBasicInfoInput {
    private UUID roomId;
}
