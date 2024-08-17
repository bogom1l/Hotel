package com.tinqinacademy.hotel.api.validation.bedsize;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) // will be applied to fields
@Retention(RetentionPolicy.RUNTIME) // will be available on runtime
@Constraint(validatedBy = BedSizeValidator.class) // implement custom validation logic from the BedSizeValidator class
public @interface BedSizeValidation {
    String message() default "Invalid bed size";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean optional() default false;
}
