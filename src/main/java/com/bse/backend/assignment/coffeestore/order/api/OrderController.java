package com.bse.backend.assignment.coffeestore.order.api;

import com.bse.backend.assignment.coffeestore.common.exception.NotFoundException;
import com.bse.backend.assignment.coffeestore.common.model.ErrorResponse;
import com.bse.backend.assignment.coffeestore.common.model.ValidationErrorResponse;
import com.bse.backend.assignment.coffeestore.order.api.model.InputOrder;
import com.bse.backend.assignment.coffeestore.order.api.model.Order;
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
 * Coffee Store Service APIs for managing orders.
 * This interface defines endpoints for listing, retrieving, creating, updating, and deleting orders.
 */
@Tag(name = "Coffee Store Service APIs: Orders", description = "API endpoints for manage orders")
@Validated
public interface OrderController {

    /**
     * Get a list of all orders for the current user.
     *
     * @return A list of orders retrieved successfully.
     * @throws NotFoundException if no orders are found.
     */
    @Operation(summary = "List all orders for current user")
    @ApiResponse(responseCode = "200", description = "Orders retrieved successfully", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = Order.class)
    ))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class)
    ))
    ResponseEntity<List<Order>> getAllOrders() throws NotFoundException;

    /**
     * Get an order by its ID.
     *
     * @param id The ID of the order to retrieve.
     * @return The order retrieved successfully.
     * @throws NotFoundException if the order is not found.
     */
    @Operation(summary = "Get an order by ID")
    @ApiResponse(responseCode = "200", description = "Order retrieved successfully", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = Order.class)
    ))
    @ApiResponse(responseCode = "404", description = "Order not found", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class)
    ))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class)
    ))
    ResponseEntity<Order> getOrderById(Long id) throws NotFoundException;

    /**
     * Create a new order for the current user.
     *
     * @param inputOrder The order to create.
     * @return The order created successfully.
     * @throws NotFoundException if the specified order items are not found
     */
    @Operation(summary = "Create a new order for current user")
    @ApiResponse(responseCode = "200", description = "Order created successfully", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = Order.class)
    ))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ValidationErrorResponse.class)
    ))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class)
    ))
    ResponseEntity<Order> createOrder(@Valid InputOrder inputOrder) throws NotFoundException;

    /**
     * Update an existing order.
     *
     * @param id         The ID of the order to update.
     * @param inputOrder The updated order information.
     * @return The order updated successfully.
     * @throws NotFoundException if the specified order or order items are not found
     */
    @Operation(summary = "Update an existing order")
    @ApiResponse(responseCode = "200", description = "Order updated successfully", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = Order.class)
    ))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ValidationErrorResponse.class)
    ))
    @ApiResponse(responseCode = "404", description = "Order not found", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class)
    ))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class)
    ))
    ResponseEntity<Order> updateOrder(Long id, @Valid InputOrder inputOrder) throws NotFoundException;

    /**
     * Delete an order by its ID.
     *
     * @param id The ID of the order to delete.
     * @return A response indicating the order was deleted successfully.
     */
    @Operation(summary = "Delete a order by ID")
    @ApiResponse(responseCode = "204", description = "Order deleted successfully")
    @ApiResponse(responseCode = "404", description = "Order not found", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class)
    ))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class)
    ))
    ResponseEntity<Void> deleteOrder(Long id);

}
