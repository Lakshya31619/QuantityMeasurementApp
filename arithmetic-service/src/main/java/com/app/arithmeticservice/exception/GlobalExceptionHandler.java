package com.app.arithmeticservice.exception;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ArithmeticServiceException.class)
    public ResponseEntity<?> handle(ArithmeticServiceException e) { return ResponseEntity.badRequest().body(Map.of("error", e.getMessage())); }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAll(Exception e) { return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage())); }
}
