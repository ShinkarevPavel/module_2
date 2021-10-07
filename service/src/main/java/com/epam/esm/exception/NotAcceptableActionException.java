package com.epam.esm.exception;

public class NotAcceptableActionException extends RuntimeException{

    public NotAcceptableActionException() {
    }

    public NotAcceptableActionException(String message) {
        super(message);
    }

    public NotAcceptableActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAcceptableActionException(Throwable cause) {
        super(cause);
    }
}
