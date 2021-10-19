package com.epam.esm.validator;


import com.epam.esm.dto.UserDto;

import java.lang.annotation.*;
import javax.validation.*;

@Documented
@Constraint(validatedBy = NameConstraintValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NameConstraint {
    String message() default "Invalid phone number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
