package com.epam.esm.exception;

public class NoSuchEntityFieldException extends RuntimeException {

    public NoSuchEntityFieldException() {
    }

    public NoSuchEntityFieldException(String message) {
        super(message);
    }

    public NoSuchEntityFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchEntityFieldException(Throwable cause) {
        super(cause);
    }
}
