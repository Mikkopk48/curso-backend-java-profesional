package dev.fintechlab.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class) ProblemDetail notFound(ResourceNotFoundException ex,HttpServletRequest r){return problem(ex,HttpStatus.NOT_FOUND,r);}
    @ExceptionHandler(BusinessConflictException.class) ProblemDetail conflict(BusinessConflictException ex,HttpServletRequest r){return problem(ex,HttpStatus.CONFLICT,r);}
    @ExceptionHandler(InvalidRequestException.class) ProblemDetail invalid(InvalidRequestException ex,HttpServletRequest r){return problem(ex,HttpStatus.BAD_REQUEST,r);}
    @ExceptionHandler(DataIntegrityViolationException.class) ProblemDetail duplicate(DataIntegrityViolationException ex,HttpServletRequest r){return problem(new BusinessConflictException("CONCURRENT_CONFLICT","La operación compitió con otra petición"),HttpStatus.CONFLICT,r);}
    @ExceptionHandler(MethodArgumentNotValidException.class) ProblemDetail validation(MethodArgumentNotValidException ex,HttpServletRequest r){var p=ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,"La petición contiene campos inválidos");p.setTitle("Validación rechazada");p.setType(URI.create("https://fintechlab.invalid/problems/validation"));p.setProperty("code","VALIDATION_ERROR");p.setProperty("correlationId",r.getAttribute("correlationId"));p.setProperty("invalidFields",ex.getBindingResult().getFieldErrors().stream().map(e->Map.of("field",e.getField(),"message",Objects.requireNonNullElse(e.getDefaultMessage(),"inválido"))).toList());return p;}
    @ExceptionHandler(Exception.class) ProblemDetail unexpected(Exception ex,HttpServletRequest r){var p=ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,"Ocurrió un error interno");p.setTitle("Error interno");p.setProperty("code","INTERNAL_ERROR");p.setProperty("correlationId",r.getAttribute("correlationId"));return p;}
    private ProblemDetail problem(DomainException ex,HttpStatus status,HttpServletRequest r){var p=ProblemDetail.forStatusAndDetail(status,ex.getMessage());p.setTitle(status.getReasonPhrase());p.setType(URI.create("https://fintechlab.invalid/problems/"+ex.code().toLowerCase(Locale.ROOT).replace('_','-')));p.setProperty("code",ex.code());p.setProperty("correlationId",r.getAttribute("correlationId"));return p;}
}
