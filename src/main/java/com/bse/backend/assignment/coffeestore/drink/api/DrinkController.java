package com.bse.backend.assignment.coffeestore.drink.api;

import com.bse.backend.assignment.coffeestore.common.model.ErrorResponse;
import com.bse.backend.assignment.coffeestore.common.model.ValidationErrorResponse;
import com.bse.backend.assignment.coffeestore.drink.api.exception.DrinkNotFoundException;
import com.bse.backend.assignment.coffeestore.drink.api.model.Drink;
import com.bse.backend.assignment.coffeestore.drink.api.model.InputDrink;
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
 * This interface defines the Coffee Store Service APIs for managing drinks.
 * It provides endpoints to list, retrieve, create, update, and delete drinks.
 */
@Tag(name = "Coffee Store Service APIs: Drinks", description = "API endpoints for manage drinks")
@Validated
public interface DrinkController {

    /**
     * Retrieves a list of all available drinks.
     *
     * @return A list of {@link Drink} objects representing the available drinks.
     */
    @Operation(summary = "List all drinks")
    @ApiResponse(responseCode = "200", description = "Drinks retrieved successfully", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = Drink.class)
    ))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class)
    ))
    ResponseEntity<List<Drink>> getAllDrinks();

    /**
     * Retrieves a specific drink by its unique identifier.
     *
     * @param id The unique identifier of the drink.
     * @return The {@link Drink} object representing the retrieved drink.
     *
     * @throws DrinkNotFoundException if the specified drink is not found.
     */
    @Operation(summary = "Get a drink by ID")
    @ApiResponse(responseCode = "200", description = "Drink retrieved successfully", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = Drink.class)
    ))
    @ApiResponse(responseCode = "404", description = "Drink not found", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class)
    ))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class)
    ))
    ResponseEntity<Drink> getDrinkById(Long id) throws DrinkNotFoundException;

    /**
     * Creates a new drink based on the provided information.
     *
     * @param newDrink The {@link InputDrink} object containing the details of the new drink.
     * @return The {@link Drink} object representing the newly created drink.
     */
    @Operation(summary = "Create a new drink")
    @ApiResponse(responseCode = "200", description = "Drink created successfully", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = Drink.class)
    ))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ValidationErrorResponse.class)
    ))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class)
    ))
    ResponseEntity<Drink> createDrink(@Valid InputDrink newDrink);

    /**
     * Updates an existing drink based on the provided information.
     *
     * @param id The unique identifier of the drink to be updated.
     * @param newDrink The {@link InputDrink} object containing the updated details of the drink.
     * @return The {@link Drink} object representing the updated drink.
     *
     * @throws DrinkNotFoundException if the specified drink is not found.
     */
    @Operation(summary = "Update an existing drink")
    @ApiResponse(responseCode = "200", description = "Drink updated successfully", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = Drink.class)
    ))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ValidationErrorResponse.class)
    ))
    @ApiResponse(responseCode = "404", description = "Drink not found", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class)
    ))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class)
    ))
    ResponseEntity<Drink> updateDrink(Long id, @Valid InputDrink newDrink) throws DrinkNotFoundException;

    /**
     * Deletes a drink by its unique identifier.
     *
     * @param id The unique identifier of the drink to be deleted.
     */
    @Operation(summary = "Delete a drink by ID")
    @ApiResponse(responseCode = "204", description = "Drink deleted successfully")
    @ApiResponse(responseCode = "404", description = "Drink not found", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class)
    ))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class)
    ))
    ResponseEntity<Void> deleteDrink(Long id);

}
