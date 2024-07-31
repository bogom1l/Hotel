package com.tinqinacademy.hotel.core.processors.base;

import jakarta.validation.*;
import org.springframework.core.convert.ConversionService;

import java.util.Set;

public abstract class BaseOperationProcessor<OperationInput> {

    private final ConversionService conversionService;
    private final Validator validator;

    protected BaseOperationProcessor(ConversionService conversionService, Validator validator) {
        this.conversionService = conversionService;
        this.validator = validator;
    }

    protected void validateInput(OperationInput input) {
        //ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
       // Validator validator = validatorFactory.getValidator();

        //Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        Set<ConstraintViolation<OperationInput>> violations = validator.validate(input);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

}
