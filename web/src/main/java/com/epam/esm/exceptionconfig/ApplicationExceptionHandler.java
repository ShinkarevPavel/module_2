package com.epam.esm.exceptionconfig;

import com.epam.esm.exception.EntityFieldValidationException;
import com.epam.esm.exception.NoSuchEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_CODE = "errorCode";

    @ExceptionHandler(NoSuchEntityException.class)
    public ResponseEntity<Object> handleEntryNotFoundException(NoSuchEntityException e) {
        Map<String, Object> response = new HashMap<>();

        response.put(ERROR_MESSAGE, e.getMessage());
        response.put(ERROR_CODE, 40401);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityFieldValidationException.class)
    public ResponseEntity<Object> handleEntryNotFoundException(EntityFieldValidationException e) {
        Map<String, Object> response = new HashMap<>();
        response.put(ERROR_MESSAGE, e.getMessage());
        response.put(ERROR_CODE, 40601);
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }
}

