package com.bse.backend.assignment.coffeestore.topping.internal.controller;

import com.bse.backend.assignment.coffeestore.common.model.ErrorResponse;
import com.bse.backend.assignment.coffeestore.topping.api.exception.ToppingNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class ToppingExceptionHandler {

    @ExceptionHandler(ToppingNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleToppingNotFoundException(ToppingNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
