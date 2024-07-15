package com.tinqinacademy.hotel.api.customvalidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.Set;

public class RoomNumberValidator implements ConstraintValidator<RoomNumberValidation, String> {

    private static final String ROOM_NUMBER_PATTERN = "^[A-Z][1-9][0-9]{2}$";
    private static final Set<String> RESERVED_ROOM_NUMBERS = new HashSet<>();
    private static final String RESERVED_ROOM_MESSAGE = "This room is reserved for VIPs only.";

    static {
        for (int i = 100; i <= 999; i++) {
            RESERVED_ROOM_NUMBERS.add(String.format("Z%d", i));
            RESERVED_ROOM_NUMBERS.add(String.format("VIP%d", i));
        }
    }

    @Override
    public void initialize(RoomNumberValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String roomNumber, ConstraintValidatorContext constraintValidatorContext) {
        if (roomNumber == null || roomNumber.isEmpty()) {
            return false;
        }

        if (RESERVED_ROOM_NUMBERS.contains(roomNumber)) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(RESERVED_ROOM_MESSAGE)
                    .addConstraintViolation();
            return false;
        }

        return roomNumber.matches(ROOM_NUMBER_PATTERN);
    }
}