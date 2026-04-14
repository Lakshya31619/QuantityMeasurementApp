package com.app.historyservice.exception;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception e) { return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage())); }
}
