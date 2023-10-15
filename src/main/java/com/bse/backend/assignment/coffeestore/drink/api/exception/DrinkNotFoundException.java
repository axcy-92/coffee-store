package com.bse.backend.assignment.coffeestore.drink.api.exception;

import com.bse.backend.assignment.coffeestore.common.exception.NotFoundException;

import java.io.Serial;

public class DrinkNotFoundException extends NotFoundException {

    @Serial
    private static final long serialVersionUID = -7432887386629639239L;

    public DrinkNotFoundException(String message) {
        super(message);
    }

}
