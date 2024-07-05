package com.tinqinacademy.hotel.model.input;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BookRoomInput {

    @JsonIgnore
    private String roomId;

    private String startDate;

    private String endDate;

    private String firstName;

    private String lastName;

    private String phoneNo;
}
