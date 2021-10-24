package com.epam.esm.exception;

public class EntityFieldValidationException extends RuntimeException {

    private String value;
    public EntityFieldValidationException() {
    }

    public EntityFieldValidationException(String message) {
        super(message);
    }

    public EntityFieldValidationException(String message, String value) {
        super(message);
        this.value = value;
    }

    public EntityFieldValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityFieldValidationException(Throwable cause) {
        super(cause);
    }

    public String getValue() {
        return value;
    }
}
