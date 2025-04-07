package com.controle.estoque.adapters.exceptions;

import com.controle.estoque.application.exceptions.InsufficientStockException;
import com.controle.estoque.application.exceptions.InvalidQuantityException;
import com.controle.estoque.application.exceptions.ProductAlreadyExistsException;
import com.controle.estoque.application.exceptions.ProductNotFoundException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJsonParseException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        String message = "Invalid request. Please check if all fields have been filled out correctly.";

        if (ex.getCause() instanceof MismatchedInputException) {
            MismatchedInputException cause = (MismatchedInputException) ex.getCause();

            if (cause.getPath().size() > 0) {
                String fieldName = cause.getPath().get(0).getFieldName();
                message = "The field '" + fieldName + "' cannot be empty or is incorrectly formatted.";
            }
        }

        return response(message, request, HttpStatus.BAD_REQUEST, LocalDateTime.now());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handling404Error(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationError>> handling400Error(MethodArgumentNotValidException exception){
        var erros = exception.getBindingResult().getFieldErrors();
        List<ValidationError> validationErrors = erros.stream().map(ValidationError::new).toList();
        return ResponseEntity.badRequest()
                .body(validationErrors);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException ex, HttpServletRequest request) {
        return response(ex.getMessage(), request, HttpStatus.NOT_FOUND, LocalDateTime.now());
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientStockException(InsufficientStockException ex, HttpServletRequest request) {
        return response(ex.getMessage(), request, HttpStatus.BAD_REQUEST, LocalDateTime.now());
    }

    @ExceptionHandler(InvalidQuantityException.class)
    public ResponseEntity<ErrorResponse> handleInvalidQuantityException(InvalidQuantityException ex, HttpServletRequest request) {
        return response(ex.getMessage(), request, HttpStatus.BAD_REQUEST, LocalDateTime.now());
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleProductAlreadyExistsException(ProductAlreadyExistsException ex, HttpServletRequest request) {
        return response(ex.getMessage(), request, HttpStatus.CONFLICT, LocalDateTime.now());
    }

    public ResponseEntity<ErrorResponse> response(final String message, final HttpServletRequest request, final HttpStatus status, LocalDateTime date) {
        return ResponseEntity.status(status)
                .body(new ErrorResponse(message,date, status.value(), request.getRequestURI()));
    }
}
