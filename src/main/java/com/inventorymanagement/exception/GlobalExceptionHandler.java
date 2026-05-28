package com.inventorymanagement.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            ResourceNotFoundException.class)

    @ResponseStatus(
            HttpStatus.NOT_FOUND)

    public Map<String, Object>
    handleResourceNotFoundException(
            ResourceNotFoundException ex) {

        Map<String, Object> error =
                new HashMap<>();

        error.put(
                "timestamp",
                LocalDateTime.now());

        error.put(
                "status",
                404);

        error.put(
                "error",
                "Not Found");

        error.put(
                "message",
                ex.getMessage());

        return error;
    }
}