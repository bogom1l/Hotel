package com.tinqinacademy.hotel.model.operations.registervisitor;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RegisterVisitorInput {
    private List<VisitorInput> visitorList;
}
