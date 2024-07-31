package com.tinqinacademy.hotel.core.processors.base;

import jakarta.validation.*;
import org.springframework.core.convert.ConversionService;

import java.util.Set;

public abstract class BaseOperationProcessor<T> {

    private final ConversionService conversionService;

    protected BaseOperationProcessor(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    protected void validateInput(T input) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        Set<ConstraintViolation<T>> violations = validator.validate(input);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

}
