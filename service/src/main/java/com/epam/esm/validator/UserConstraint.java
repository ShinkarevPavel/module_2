package com.epam.esm.validator;

import org.springframework.context.annotation.PropertySource;

import java.lang.annotation.*;
import javax.validation.*;

@Constraint(validatedBy = UserConstraintValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserConstraint {
    String message() default "{defauuser.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
