package com.tinqinacademy.hotel.persistence.model.operations.system.registervisitor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterVisitorInput {
//    @Valid
//    @NotEmpty(message = "Visitor list should be not empty")
    private List<Visitor> visitors;
}
