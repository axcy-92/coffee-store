package com.bse.backend.assignment.coffeestore.order.api.exception;

import com.bse.backend.assignment.coffeestore.common.exception.NotFoundException;

import java.io.Serial;

public class OrderNotFoundException extends NotFoundException {

    @Serial
    private static final long serialVersionUID = -2525841757623002564L;

    public OrderNotFoundException(String message) {
        super(message);
    }

}
