package com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.api.base.OperationInput;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
public class GetBookingHistoryInput implements OperationInput {

    @JsonIgnore
    private String userId;

}
