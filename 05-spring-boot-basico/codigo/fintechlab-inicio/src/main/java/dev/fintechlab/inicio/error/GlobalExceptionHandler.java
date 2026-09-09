package dev.fintechlab.inicio.error;

import java.time.Instant;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    ResponseEntity<ApiError> handleNotFound(CustomerNotFoundException exception) {
        return response(HttpStatus.NOT_FOUND, "CUSTOMER_NOT_FOUND", exception.getMessage(), List.of());
    }

    @ExceptionHandler(CustomerEmailAlreadyExistsException.class)
    ResponseEntity<ApiError> handleConflict(CustomerEmailAlreadyExistsException exception) {
        return response(HttpStatus.CONFLICT, "CUSTOMER_EMAIL_ALREADY_EXISTS", exception.getMessage(), List.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException exception) {
        List<ApiError.FieldViolation> violations = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ApiError.FieldViolation(error.getField(), error.getDefaultMessage()))
                .toList();

        return response(
                HttpStatus.BAD_REQUEST,
                "INVALID_REQUEST",
                "La petición contiene campos inválidos",
                violations);
    }

    private ResponseEntity<ApiError> response(
            HttpStatus status,
            String code,
            String message,
            List<ApiError.FieldViolation> violations) {
        ApiError body = new ApiError(code, message, Instant.now(), violations);
        return ResponseEntity.status(status).body(body);
    }
}
