package com.bse.backend.assignment.coffeestore.security.internal.controller;

import com.bse.backend.assignment.coffeestore.common.model.ErrorResponse;
import com.bse.backend.assignment.coffeestore.security.api.exception.UserAlreadyExistsException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class AuthenticationExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        log.error("Access Denied: " + ex.getMessage(), ex);
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.UNAUTHORIZED.getReasonPhrase(), ex.getMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        log.error("Access Denied: " + ex.getMessage(), ex);
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.FORBIDDEN.getReasonPhrase(), ex.getMessage()),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException ex) {
        log.error("JWT expired: " + ex.getMessage(), ex);
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.UNAUTHORIZED.getReasonPhrase(), ex.getMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    ResponseEntity<ErrorResponse> onAuthenticationException(AuthenticationException ex) {
        log.error("Exception occurred while trying to authenticate", ex);
        var response = new ErrorResponse(HttpStatus.FORBIDDEN.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

}
