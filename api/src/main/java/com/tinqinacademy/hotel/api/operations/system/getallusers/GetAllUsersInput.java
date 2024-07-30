package com.tinqinacademy.hotel.api.operations.system.getallusers;

import com.tinqinacademy.hotel.api.base.OperationInput;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GetAllUsersInput implements OperationInput {
    private String partialName;
}
