package com.tinqinacademy.hotel.persistence.model.operations.hotel.updatepartiallybooking;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UpdatePartiallyGuestInput {
    @NotBlank(message = "firstname is mandatory")
    private String firstName;

    @NotBlank(message = "lastName is mandatory")
    private String lastName;

    @NotBlank(message = "phoneNumber is mandatory")
    private String phoneNumber;

    @NotBlank(message = "idCardNumber is mandatory")
    private String idCardNumber;

    @NotNull(message = "idCardValidity is mandatory")
    private LocalDate idCardValidity;

    @NotBlank(message = "idCardIssueAuthority is mandatory")
    private String idCardIssueAuthority;

    @NotNull(message = "idCardIssueDate is mandatory")
    private LocalDate idCardIssueDate;

    @NotNull(message = "Birthdate is mandatory")
    private LocalDate birthdate;
}
