package com.bse.backend.assignment.coffeestore.security.api.exception;

import java.io.Serial;

public class UserAlreadyExistsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -1883348115226087221L;

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
