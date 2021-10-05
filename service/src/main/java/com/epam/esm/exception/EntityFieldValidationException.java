package com.epam.esm.exception;

public class EntityFieldValidationException extends RuntimeException {

    public EntityFieldValidationException() {
    }

    public EntityFieldValidationException(String message) {
        super(message);
    }

    public EntityFieldValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityFieldValidationException(Throwable cause) {
        super(cause);
    }
}
