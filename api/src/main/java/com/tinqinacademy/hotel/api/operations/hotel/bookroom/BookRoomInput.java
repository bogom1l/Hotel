package com.tinqinacademy.hotel.api.operations.hotel.bookroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.api.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
public class BookRoomInput implements OperationInput {
    @JsonIgnore
    private String roomId;

    @NotNull(message = "Start date is mandatory")
    private LocalDate startDate;

    @NotNull(message = "End date is mandatory")
    private LocalDate endDate;

//    @NotBlank(message = "First name is mandatory")
//    private String firstName;
//
//    @NotBlank(message = "Last name is mandatory")
//    private String lastName;
//
//    @NotBlank(message = "Phone number is mandatory")
//    private String phoneNumber;

    @NotBlank(message = "User id is mandatory")
    private String userId;
}
