package com.epam.esm.validator;

public class EntityValidator {
    private static final long DURATION_PATTERN = 10;
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
}
