package com.tinqinacademy.hotel.model.operations.registervisitor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RegisterVisitorInput {

    @Valid
    @NotEmpty(message = "Visitor list should be not empty")
    private List<VisitorInput> visitorList;
}
