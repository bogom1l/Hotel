package com.tinqinacademy.hotel.model.operations.registervisitor;

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
public class VisitorInput {

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

    @NotBlank(message = "ID Card number is mandatory")
    private String idCardNo;

    @NotBlank(message = "ID Card validity is mandatory")
    private String idCardValidity; // TODO: ? LocalDate and @NotNull

    @NotBlank(message = "ID Card issue authority is mandatory")
    private String idCardIssueAuthority;

    @NotBlank(message = "ID Card issue date is mandatory")
    private String idCardIssueDate; // TODO: ? LocalDate and @NotNull
}
