package dev.talha.sis.student.web;

import dev.talha.sis.student.exception.ConflictException;
import dev.talha.sis.student.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFound(){ return ResponseEntity.status(404).body(Map.of("error","not_found")); }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> conflict(ConflictException e){
        return ResponseEntity.status(409).body(Map.of("error", e.getMessage()));
    }
}
