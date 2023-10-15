package com.bse.backend.assignment.coffeestore.common.exception;

import java.io.Serial;

public class NotFoundException extends Exception {

    @Serial
    private static final long serialVersionUID = 5690088449379635022L;

    public NotFoundException(String message) {
        super(message);
    }
}
