package com.tinqinacademy.hotel.persistence.model.operations.system.registerguest;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GuestInput {

    @NotNull(message = "Start date is mandatory")
    private LocalDate startDate;

    @NotNull(message = "End date is mandatory")
    private LocalDate endDate;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @NotBlank(message = "Phone number is mandatory")
    private String phoneNo;

    @NotBlank(message = "Id card number is mandatory")
    private String idCardNo;

    @NotNull(message = "Id card validity date is mandatory")
    private LocalDate idCardValidity;

    @NotBlank(message = "Id card issue authority is mandatory")
    private String idCardIssueAuthority;

    @NotNull(message = "Id card issue date is mandatory")
    private LocalDate idCardIssueDate;

    @NotBlank(message = "Room id is mandatory")
    private String roomId;

    @NotNull(message = "Birth date is mandatory")
    private LocalDate birthdate;

}
