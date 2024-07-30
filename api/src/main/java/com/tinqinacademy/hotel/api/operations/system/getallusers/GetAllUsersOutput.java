package com.tinqinacademy.hotel.api.operations.system.getallusers;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllUsersOutput implements OperationOutput {
    private List<UserOutput> users;

    private Integer count;
}
