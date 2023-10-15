package com.bse.backend.assignment.coffeestore.security.api;

import com.bse.backend.assignment.coffeestore.security.api.model.AuthenticationResponse;
import com.bse.backend.assignment.coffeestore.security.api.model.SignInRequest;
import com.bse.backend.assignment.coffeestore.security.api.model.SignUpRequest;
import com.bse.backend.assignment.coffeestore.security.api.model.UserResponse;

import java.util.Optional;

/**
 * A service interface for managing user-related operations
 */
public interface UserService {

    /**
     * Create a new user by registering with the provided user details.
     *
     * @param request The user registration request containing user details.
     * @return An authentication response containing user information and an authentication token upon successful registration.
     */
    AuthenticationResponse create(SignUpRequest request);

    /**
     * Authenticate and log in a user using the provided credentials.
     *
     * @param request The user login request containing login credentials.
     * @return An authentication response containing user information and an authentication token upon successful login.
     */
    AuthenticationResponse login(SignInRequest request);

    /**
     * Retrieve the currently authenticated user, if available.
     *
     * @return An optional user response, which may contain user details if a user is authenticated.
     */
    Optional<UserResponse> getCurrentUser();
}
