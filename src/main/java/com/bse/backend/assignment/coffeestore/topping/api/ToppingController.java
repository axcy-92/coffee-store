package com.bse.backend.assignment.coffeestore.topping.api;

import com.bse.backend.assignment.coffeestore.common.model.ErrorResponse;
import com.bse.backend.assignment.coffeestore.common.model.ValidationErrorResponse;
import com.bse.backend.assignment.coffeestore.topping.api.exception.ToppingNotFoundException;
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

/**
 * This interface defines the Coffee Store Service APIs for managing toppings.
 * It provides endpoints to list, retrieve, create, update, and delete toppings.
 */
@Tag(name = "Coffee Store Service APIs: Toppings", description = "API endpoints for manage toppings")
@Validated
public interface ToppingController {

    /**
     * Retrieves a list of all available toppings.
     *
     * @return A list of {@link Topping} objects representing the available toppings.
     */
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

    /**
     * Retrieves a specific topping by its unique identifier.
     *
     * @param id The unique identifier of the topping.
     * @return The {@link Topping} object representing the retrieved topping.
     *
     * @throws ToppingNotFoundException if the specified topping is not found.
     */
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
    ResponseEntity<Topping> getToppingById(Long id) throws ToppingNotFoundException;

    /**
     * Creates a new topping based on the provided information.
     *
     * @param newTopping The {@link InputTopping} object containing the details of the new topping.
     * @return The {@link Topping} object representing the newly created topping.
     */
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

    /**
     * Updates an existing topping based on the provided information.
     *
     * @param id The unique identifier of the topping to be updated.
     * @param newTopping The {@link InputTopping} object containing the updated details of the topping.
     * @return The {@link Topping} object representing the updated topping.
     *
     * @throws ToppingNotFoundException if the specified topping is not found.
     */
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
    ResponseEntity<Topping> updateTopping(Long id, @Valid InputTopping newTopping) throws ToppingNotFoundException;

    /**
     * Deletes a topping by its unique identifier.
     *
     * @param id The unique identifier of the topping to be deleted.
     */
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
