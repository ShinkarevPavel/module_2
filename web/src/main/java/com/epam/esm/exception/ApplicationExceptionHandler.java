package com.epam.esm.exception;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.PersistenceException;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
class ApplicationExceptionHandler {
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_CODE = "errorCode";
    private final ResourceBundleMessageSource messages;

    @Autowired
    public ApplicationExceptionHandler(ResourceBundleMessageSource messages) {
        this.messages = messages;
    }

    @ExceptionHandler(NoSuchEntityException.class)
    public ResponseEntity<Object> handleEntryNotFoundException(NoSuchEntityException e, Locale locale) {
        Map<String, Object> response = new HashMap<>();
        response.put(ERROR_MESSAGE, messages.getMessage(e.getMessage(), null, locale) + e.getValue());
        response.put(ERROR_CODE, 40401);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityFieldValidationException.class)
    public ResponseEntity<Object> handleEntryNotFoundException(EntityFieldValidationException e, Locale locale) {
        Map<String, Object> response = new HashMap<>();
        response.put(ERROR_MESSAGE, messages.getMessage(e.getMessage(), null, locale) + (Objects.nonNull(e.getValue()) ? e.getValue() : ""));
        response.put(ERROR_CODE, 40601);
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(NoSuchEntityFieldException.class)
    public ResponseEntity<Object> handleEntryNotFoundException(NoSuchEntityFieldException e, Locale locale) {
        Map<String, Object> response = new HashMap<>();
        response.put(ERROR_MESSAGE, messages.getMessage(getMessage(40901), null, locale));
        response.put(ERROR_CODE, 40901);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntryAlreadyExistsException.class)
    public ResponseEntity<Object> handleEntryNotFoundException(EntryAlreadyExistsException e, Locale locale) {
        Map<String, Object> response = new HashMap<>();
        response.put(ERROR_MESSAGE, messages.getMessage(e.getMessage(), null, locale));
        response.put(ERROR_CODE, 40901);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotAcceptableActionException.class)
    public ResponseEntity<Object> handleEntryNotFoundException(NotAcceptableActionException e, Locale locale) {
        Map<String, Object> response = new HashMap<>();
        response.put(ERROR_MESSAGE, messages.getMessage(e.getMessage(), null, locale));
        response.put(ERROR_CODE, 40901);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = resolveBindingResultErrors(e.getBindingResult());

        return new ResponseEntity<>(createResponse(40001, message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnacceptableRemoveEntityException.class)
    public ResponseEntity<Object> handleEntryNotFoundException(UnacceptableRemoveEntityException e, Locale locale) {
        Map<String, Object> response = new HashMap<>();
        response.put(ERROR_MESSAGE, messages.getMessage(e.getMessage(), null, locale));
        response.put(ERROR_CODE, 40601);
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }


    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(ValidationException e) {
        Map<String, Object> response = new HashMap<>();
        response.put(ERROR_MESSAGE, e.getMessage());
        response.put(ERROR_CODE, 40001);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Map<String, Object> createResponse(int errorCode, String errorDescription) {
        Map<String, Object> response = new HashMap<>();
        response.put(ERROR_MESSAGE, errorDescription);
        response.put(ERROR_CODE, errorCode);
        return response;
    }


    private String resolveBindingResultErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .map(fr -> {
                    String field = fr.getField();
                    String validationMessage = fr.getDefaultMessage();
                    return String.format("'%s': %s", field, validationMessage);
                })
                .collect(Collectors.joining(", "));
    }

    private String getMessage(int errorCode) {
        return "error_code." + errorCode;
    }
}

