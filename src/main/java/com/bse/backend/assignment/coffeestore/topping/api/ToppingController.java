package com.bse.backend.assignment.coffeestore.topping.api;

import com.bse.backend.assignment.coffeestore.common.model.ErrorResponse;
import com.bse.backend.assignment.coffeestore.common.model.ValidationErrorResponse;
import com.bse.backend.assignment.coffeestore.topping.api.model.InputTopping;
import com.bse.backend.assignment.coffeestore.topping.api.model.Topping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Coffee Store Service APIs: Toppings", description = "API endpoints for manage toppings")
@Validated
public interface ToppingController {

    @Operation(summary = "List all toppings")
    @ApiResponse(responseCode = "200", description = "Toppings retrieved successfully", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = Topping.class)
    ))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class)
    ))
    ResponseEntity<List<Topping>> getAllToppings();

    @Operation(summary = "Get a topping by ID")
    @ApiResponse(responseCode = "200", description = "Topping retrieved successfully", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = Topping.class)
    ))
    @ApiResponse(responseCode = "404", description = "Topping not found", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class)
    ))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class)
    ))
    ResponseEntity<Topping> getToppingById(Long id);

    @Operation(summary = "Create a new topping")
    @ApiResponse(responseCode = "200", description = "Topping created successfully", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = Topping.class)
    ))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ValidationErrorResponse.class)
    ))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class)
    ))
    ResponseEntity<Topping> createTopping(@Valid InputTopping newTopping);

    @Operation(summary = "Update an existing topping")
    @ApiResponse(responseCode = "200", description = "Topping updated successfully", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = Topping.class)
    ))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ValidationErrorResponse.class)
    ))
    @ApiResponse(responseCode = "404", description = "Topping not found", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class)
    ))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class)
    ))
    ResponseEntity<Topping> updateTopping(Long id, @Valid InputTopping newTopping);

    @Operation(summary = "Delete a topping by ID")
    @ApiResponse(responseCode = "204", description = "Topping deleted successfully")
    @ApiResponse(responseCode = "404", description = "Topping not found", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class)
    ))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class)
    ))
    ResponseEntity<Void> deleteTopping(Long id);
}
