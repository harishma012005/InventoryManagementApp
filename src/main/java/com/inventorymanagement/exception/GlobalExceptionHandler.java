package com.inventorymanagement.exception;

import java.util.HashMap;

import java.util.Map;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Resource Not Found Exception
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleResourceNotFoundException(
            ResourceNotFoundException ex) {

        Map<String, Object> error = new HashMap<>();

        error.put("status", 404);
        error.put("message", ex.getMessage());

        return error;
    }

    // Already Exists Exception
    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleAlreadyExistsException(
            AlreadyExistsException ex) {

        Map<String, Object> error = new HashMap<>();

        error.put("status", 409);
        error.put("message", ex.getMessage());

        return error;
    }

    // Validation Exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, Object> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {

                    errors.put(
                            error.getField(),
                            error.getDefaultMessage());
                });

        return errors;
    }

    // Runtime Exception
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleRuntimeException(
            RuntimeException ex) {

        Map<String, Object> error = new HashMap<>();

        error.put("status", 400);
        error.put("message", ex.getMessage());

        return error;
    }
}