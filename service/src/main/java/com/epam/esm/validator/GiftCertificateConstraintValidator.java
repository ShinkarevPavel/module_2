package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.validator.annotation.OrderCertificateConstraint;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Objects;

public class GiftCertificateConstraintValidator implements ConstraintValidator<OrderCertificateConstraint, List<GiftCertificateDto>> {

    @Override
    public void initialize(OrderCertificateConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<GiftCertificateDto> value, ConstraintValidatorContext context) {
        if (CollectionUtils.isEmpty(value)) {
            return false;
        }
        for (GiftCertificateDto g : value) {
            if (Objects.isNull(g.getId()) || g.getId() < 1) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("{giftcertificate.id.message}").addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
