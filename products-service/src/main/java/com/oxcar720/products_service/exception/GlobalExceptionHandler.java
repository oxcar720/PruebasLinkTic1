package com.oxcar720.products_service.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusiness(BusinessException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Map.of(
                        "errors", List.of(
                                Map.of(
                                        "status", "409",
                                        "detail", ex.getMessage()
                                )
                        )
                )
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {

        List<Map<String, String>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> Map.of(
                        "status", "422",
                        "detail", err.getDefaultMessage()
                ))
                .toList();

        return ResponseEntity.status(422).body(Map.of("errors", errors));
    }

    @ExceptionHandler(NoSuchElementException.class)
        public ResponseEntity<?> handleNotFound() {
        return ResponseEntity.status(404).body(
                Map.of(
                        "errors", List.of(
                                Map.of(
                                        "status", "404",
                                        "detail", "Resource not found"
                                )
                        )
                )
        );
        }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(500).body(
                Map.of(
                        "errors", List.of(
                                Map.of(
                                        "status", "500",
                                        "detail", "Internal server error"
                                )
                        )
                )
        );
    }
}