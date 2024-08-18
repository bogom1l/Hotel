package com.tinqinacademy.hotel.core.processors.base;

import com.tinqinacademy.hotel.api.error.Error;
import com.tinqinacademy.hotel.api.exceptions.ValidationException;
import com.tinqinacademy.hotel.core.errorhandler.ErrorHandler;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.core.convert.ConversionService;

import java.util.List;
import java.util.Set;

public abstract class BaseOperationProcessor<OperationInput> {
    protected final ConversionService conversionService;
    protected final ErrorHandler errorHandler;
    private final Validator validator;

    protected BaseOperationProcessor(ConversionService conversionService, ErrorHandler errorHandler, Validator validator) {
        this.conversionService = conversionService;
        this.errorHandler = errorHandler;
        this.validator = validator;
    }

    protected void validateInput(OperationInput input) {
        Set<ConstraintViolation<OperationInput>> violations = validator.validate(input);

        if (!violations.isEmpty()) {
            List<Error> errors = buildErrors(violations);

            throw new ValidationException(errors);
        }
    }

    private List<Error> buildErrors(Set<ConstraintViolation<OperationInput>> violations) {
        List<Error> errors = violations.stream()
                .map(violation -> Error.builder()
                        .field(violation.getPropertyPath().toString())
                        .message(violation.getMessage())
                        .build())
                .toList();

        return errors;
    }
}
