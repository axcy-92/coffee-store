package com.bse.backend.assignment.coffeestore.topping.api.exception;

import java.io.Serial;

public class ToppingNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 4184692617480391129L;

    public ToppingNotFoundException(String message) {
        super(message);
    }

}
