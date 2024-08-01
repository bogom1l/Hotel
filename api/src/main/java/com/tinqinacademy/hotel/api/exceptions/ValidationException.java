package com.tinqinacademy.hotel.api.exceptions;

import com.tinqinacademy.hotel.api.error.Error;
import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {
    private final List<Error> violations;

    public ValidationException(List<Error> violations) {
        this.violations = violations;
    }

}
