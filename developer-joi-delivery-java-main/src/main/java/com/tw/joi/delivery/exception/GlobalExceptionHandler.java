package com.tw.joi.delivery.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<Map<String, Object>> handleExceptions(Exception ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(),
                                error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }
}
