package com.bse.backend.assignment.coffeestore.security.api;

import com.bse.backend.assignment.coffeestore.common.model.ErrorResponse;
import com.bse.backend.assignment.coffeestore.common.model.ValidationErrorResponse;
import com.bse.backend.assignment.coffeestore.security.api.model.AuthenticationResponse;
import com.bse.backend.assignment.coffeestore.security.api.model.SignInRequest;
import com.bse.backend.assignment.coffeestore.security.api.model.SignUpRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * An interface defining API endpoints for user authentication in the Coffee Store service.
 */
@Tag(name = "Coffee Store Service APIs: Authentication", description = "API endpoints for user authentication")
@Validated
public interface AuthenticationController {

    /**
     * Sign up a new user and create a user account.
     *
     * @param request The request to sign up a new user.
     * @return A response containing user information and an authentication token upon successful sign-up.
     */
    @Operation(summary = "Sign up a new user")
    @ApiResponse(responseCode = "200", description = "User successfully signed up", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = AuthenticationResponse.class)
    ))
    @ApiResponse(responseCode = "400", description = "Validation error", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ValidationErrorResponse.class)
    ))
    @ApiResponse(responseCode = "409", description = "User already exists", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class)
    ))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class)
    ))
    ResponseEntity<AuthenticationResponse> signUp(@Valid SignUpRequest request);

    /**
     * Sign in a user and authenticate the user.
     *
     * @param request The request to sign in a user.
     * @return A response containing user information and an authentication token upon successful sign-in.
     */
    @Operation(summary = "Sign in a user")
    @ApiResponse(responseCode = "200", description = "User successfully signed in", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = AuthenticationResponse.class)
    ))
    @ApiResponse(responseCode = "400", description = "Validation error", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ValidationErrorResponse.class)
    ))
    @ApiResponse(responseCode = "401", description = "Bad credentials", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ValidationErrorResponse.class)
    ))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class)
    ))
    ResponseEntity<AuthenticationResponse> signIn(@Valid SignInRequest request);
}