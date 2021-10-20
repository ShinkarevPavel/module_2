package com.epam.esm.validator;


import java.lang.annotation.*;
import javax.validation.*;

@Constraint(validatedBy = UserConstraintValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserConstraint {
    String message() default "{default.user.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
