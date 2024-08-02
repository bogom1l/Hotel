package com.tinqinacademy.hotel.api.validation.bedsize;

import com.tinqinacademy.hotel.api.enums.BedSize;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public class BedSizeValidator implements ConstraintValidator<BedSizeValidation, String> {

    private static final Set<String> VALID_BED_SIZES = EnumSet.allOf(BedSize.class).stream()
            .map(BedSize::getCode)
            .collect(Collectors.toSet());
    private boolean optional;

    @Override
    public void initialize(BedSizeValidation constraintAnnotation) {
        this.optional = constraintAnnotation.optional();
    }

    @Override
    public boolean isValid(String bedSize, ConstraintValidatorContext constraintValidatorContext) {
        if (bedSize == null || bedSize.isEmpty()) {
            return optional;
        }
        return VALID_BED_SIZES.contains(bedSize);
    }
}

