package com.epam.esm.validator;

import com.epam.esm.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class UserConstraintValidator implements ConstraintValidator<UserConstraint, UserDto> {

    @Override
    public void initialize(UserConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserDto value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        if (Objects.isNull(value.getId()) || value.getId() <= 0) {
            context.disableDefaultConstraintViolation();

            return false;
        }
        return true;
    }
}
