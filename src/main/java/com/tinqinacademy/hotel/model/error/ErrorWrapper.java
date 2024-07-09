package com.tinqinacademy.hotel.model.error;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ErrorWrapper {
    private List<Error> errors;
    private HttpStatus errorCode;
}
