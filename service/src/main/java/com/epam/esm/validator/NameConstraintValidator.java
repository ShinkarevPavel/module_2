package com.epam.esm.validator;

import com.epam.esm.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameConstraintValidator implements ConstraintValidator<NameConstraint, UserDto> {

    @Override
    public void initialize(NameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserDto value, ConstraintValidatorContext context) {
        return false;
    }
}
