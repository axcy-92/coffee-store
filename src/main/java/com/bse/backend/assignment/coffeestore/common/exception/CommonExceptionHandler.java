package com.bse.backend.assignment.coffeestore.common.exception;

import com.bse.backend.assignment.coffeestore.common.model.ErrorResponse;
import com.bse.backend.assignment.coffeestore.common.model.ValidationErrorResponse;
import com.bse.backend.assignment.coffeestore.common.model.Violation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException ex
    ) {
        log.error("Exception occurred while trying to validate request", ex);
        List<Violation> violationList = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> Violation.builder()
                        .fieldName(fieldError.getField())
                        .errorMessage(fieldError.getDefaultMessage())
                        .build())
                .toList();

        var response = new ValidationErrorResponse(HttpStatus.BAD_REQUEST.getReasonPhrase(), violationList);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorResponse> handleConstraintViolationException(
            final ConstraintViolationException ex
    ) {
        log.error("Exception occurred while trying to validate request", ex);
        List<Violation> violationList = ex.getConstraintViolations()
                .stream()
                .map(constraintViolation -> Violation.builder()
                        .fieldName(constraintViolation.getPropertyPath().toString())
                        .errorMessage(constraintViolation.getMessage())
                        .build())
                .toList();

        var response = new ValidationErrorResponse(HttpStatus.BAD_REQUEST.getReasonPhrase(),
                violationList);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(final RuntimeException ex) {
        log.error("Unexpected exception occurred", ex);
        var response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
