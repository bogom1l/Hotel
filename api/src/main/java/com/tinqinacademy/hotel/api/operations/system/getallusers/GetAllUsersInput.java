package com.tinqinacademy.hotel.api.operations.system.getallusers;

import com.tinqinacademy.hotel.api.base.OperationInput;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GetAllUsersInput implements OperationInput {
    @Size(max = 10)
    private String partialName;
}
