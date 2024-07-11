package com.tinqinacademy.hotel.api.error;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoomNumberValidator implements ConstraintValidator<RoomNumberValidation, String> {

    private static final String ROOM_NUMBER_PATTERN = "^[a-z]+[0-9]$";

    @Override
    public void initialize(RoomNumberValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String roomNumber, ConstraintValidatorContext constraintValidatorContext) {
        if (roomNumber == null || roomNumber.isEmpty()) {
            return false;
        }

        // TODO: to add at least 1 more rule

        return roomNumber.matches(ROOM_NUMBER_PATTERN);
    }
}
