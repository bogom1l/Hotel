package com.tinqinacademy.hotel.model.input;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class BookRoomInput {

    @JsonIgnore
    private String roomId;

    private LocalDate startDate;

    private LocalDate endDate;

    private String firstName;

    private String lastName;

    private String phoneNo;
}
