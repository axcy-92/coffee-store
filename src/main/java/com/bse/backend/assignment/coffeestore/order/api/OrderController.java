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

@Tag(name = "Coffee Store Service APIs: Orders", description = "API endpoints for manage orders")
@Validated
public interface OrderController {

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
