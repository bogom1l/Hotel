package com.tinqinacademy.hotel.api.operations.system.getallusers;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllUsersOutput {
    private List<UserOutput> users;

    private Integer count;
}
