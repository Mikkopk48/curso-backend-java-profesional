package dev.fintechlab.inicio.error;

import java.time.Instant;
import java.util.List;

public record ApiError(
        String code,
        String message,
        Instant timestamp,
        List<FieldViolation> violations) {

    public record FieldViolation(String field, String message) {
    }
}
