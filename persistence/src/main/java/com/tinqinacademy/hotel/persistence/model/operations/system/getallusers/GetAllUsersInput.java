package com.tinqinacademy.hotel.persistence.model.operations.system.getallusers;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GetAllUsersInput {
    private String partialName;
}
