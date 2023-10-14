package com.bse.backend.assignment.coffeestore.security.api;

import com.bse.backend.assignment.coffeestore.common.model.ErrorResponse;
import com.bse.backend.assignment.coffeestore.common.model.ValidationErrorResponse;
import com.bse.backend.assignment.coffeestore.security.api.model.JwtAuthenticationResponse;
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

@Tag(name = "Coffee Store Service APIs: Authentication", description = "API endpoints for user authentication")
@Validated
public interface AuthenticationController {

    @Operation(summary = "Sign up a new user")
    @ApiResponse(responseCode = "200", description = "User successfully signed up", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = JwtAuthenticationResponse.class)
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
    ResponseEntity<JwtAuthenticationResponse> signUp(@Valid SignUpRequest request);

    @Operation(summary = "Sign in a user")
    @ApiResponse(responseCode = "200", description = "User successfully signed in", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = JwtAuthenticationResponse.class)
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
    ResponseEntity<JwtAuthenticationResponse> signIn(@Valid SignInRequest request);
}
