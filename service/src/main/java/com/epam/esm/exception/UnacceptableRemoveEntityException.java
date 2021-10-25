package com.epam.esm.exception;

public class UnacceptableRemoveEntityException extends RuntimeException{
    public UnacceptableRemoveEntityException() {
    }

    public UnacceptableRemoveEntityException(String message) {
        super(message);
    }

    public UnacceptableRemoveEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnacceptableRemoveEntityException(Throwable cause) {
        super(cause);
    }
}
