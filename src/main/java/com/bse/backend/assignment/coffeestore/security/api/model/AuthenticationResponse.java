package com.bse.backend.assignment.coffeestore.security.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents the response returned when a user is successfully authenticated in the Coffee Store application.
 * It includes user information and an authentication token.
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class AuthenticationResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -7516959339353014921L;

    private UserResponse user;
    private String token;
}