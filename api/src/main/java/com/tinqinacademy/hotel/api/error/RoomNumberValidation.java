package com.tinqinacademy.hotel.api.error;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RoomNumberValidator.class)
public @interface RoomNumberValidation {

    String message() default "Invalid room number: must start with few letters and then be followed by 1 number\n" + "for example: test1, abc2, room63";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
