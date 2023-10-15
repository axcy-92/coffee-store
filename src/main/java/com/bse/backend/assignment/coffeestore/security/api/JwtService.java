package com.bse.backend.assignment.coffeestore.security.api;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * A service interface for handling JWT (JSON Web Token) operations, such as token extraction, generation,
 * and validation.
 */
public interface JwtService {

    /**
     * Extract the username from a JWT token.
     *
     * @param token The JWT token from which to extract the username.
     * @return The username extracted from the token.
     */
    String extractUserName(String token);

    /**
     * Generate a JWT token based on the provided UserDetails.
     *
     * @param userDetails The user details for which to generate the token.
     * @return A JWT token generated for the given user details.
     */
    String generateToken(UserDetails userDetails);

    /**
     * Check if a JWT token is valid for the given UserDetails.
     *
     * @param token The JWT token to be validated.
     * @param userDetails The user details against which to validate the token.
     * @return `true` if the token is valid for the user details, `false` otherwise.
     */
    boolean isTokenValid(String token, UserDetails userDetails);
}