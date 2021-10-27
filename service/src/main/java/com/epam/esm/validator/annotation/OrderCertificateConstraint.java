package com.epam.esm.validator.annotation;

import com.epam.esm.validator.GiftCertificateConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = GiftCertificateConstraintValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderCertificateConstraint {
    String message() default "{default.giftcertificate.list.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
