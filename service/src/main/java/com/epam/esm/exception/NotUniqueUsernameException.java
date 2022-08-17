package com.epam.esm.exception;

public class NotUniqueUsernameException extends RuntimeException{

    public NotUniqueUsernameException(String message) {
        super(message);
    }

    public NotUniqueUsernameException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotUniqueUsernameException(Throwable cause) {
        super(cause);
    }
}
