package com.bse.backend.assignment.coffeestore.common.exception;

import java.io.Serial;

/**
 * This exception is thrown to indicate that a resource or entity was not found.
 * It is typically used to handle cases where a requested resource or object does not exist.
 */
public class NotFoundException extends Exception {

    @Serial
    private static final long serialVersionUID = 5690088449379635022L;

    /**
     * Constructs a new `NotFoundException` with the specified detail message.
     *
     * @param message the detail message
     */
    public NotFoundException(String message) {
        super(message);
    }

}
