package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.EntityFieldValidationException;

public class EntityValidator {
    private static final long DURATION_PATTERN = 365;
    private static final String NAME_PATTERN = "[a-zA-Zа-яА-ЯёЁ\\s+]{1,45}";
    private static final String DESCRIPTION_PATTERN = "[\\wа-яА-ЯёЁ\\s+]{1,80}";


    public static boolean isNameValid(String name) {
        return name.matches(NAME_PATTERN);
    }

    public static boolean isDescriptionValid(String description) {
        return description.matches(DESCRIPTION_PATTERN);
    }

    public static boolean isDurationValid(Integer duration) {
        return duration > 0 && duration <= DURATION_PATTERN;
    }

    public static boolean isPriceValid (Double price) {
        return price > 0;
    }

    public static boolean isValidEntityFieldForCreate(GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto.getName() == null || !isNameValid(giftCertificateDto.getName())) {
            throw new EntityFieldValidationException("error_message.create_name");
        }
        if (giftCertificateDto.getDescription() == null || !isDescriptionValid(giftCertificateDto.getDescription())) {
            throw new EntityFieldValidationException("error_message.create_description");
        }
        if (giftCertificateDto.getPrice() == null || !isPriceValid(giftCertificateDto.getPrice())) {
            throw new EntityFieldValidationException("error_message.create_price");
        }
        if (giftCertificateDto.getDuration() == null || !isDurationValid(giftCertificateDto.getDuration())) {
            throw new EntityFieldValidationException("error_message.create_duration");
        }
        return true;
    }
}
