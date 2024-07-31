package com.tinqinacademy.hotel.core.processors.base;

import com.tinqinacademy.hotel.api.error.ErrorHandler;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.core.convert.ConversionService;

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
            throw new ConstraintViolationException(violations);
        }
    }

}
