package com.epam.esm.exception;


public class NoSuchEntityException extends RuntimeException {


    public NoSuchEntityException() {
        super("error_code.40401");
    }

    public NoSuchEntityException(String message) {
        super(message);
    }

    public NoSuchEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchEntityException(Throwable cause) {
        super(cause);
    }

}
