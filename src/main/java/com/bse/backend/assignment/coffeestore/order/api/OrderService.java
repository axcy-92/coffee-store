package com.bse.backend.assignment.coffeestore.order.api;

import com.bse.backend.assignment.coffeestore.drink.api.exception.DrinkNotFoundException;
import com.bse.backend.assignment.coffeestore.order.api.exception.OrderNotFoundException;
import com.bse.backend.assignment.coffeestore.order.api.model.InputOrder;
import com.bse.backend.assignment.coffeestore.order.api.model.Order;

import java.util.List;

/**
 * Service interface for managing orders in the Coffee Store application.
 */
public interface OrderService {

    /**
     * Retrieve a list of all orders.
     *
     * @return A list of orders retrieved successfully.
     * @throws DrinkNotFoundException if a drink referenced in an order is not found.
     */
    List<Order> getAllOrders() throws DrinkNotFoundException;

    /**
     * Retrieve an order by its ID.
     *
     * @param id The ID of the order to retrieve.
     * @return The order retrieved successfully.
     * @throws OrderNotFoundException if the order is not found.
     * @throws DrinkNotFoundException if a drink referenced in the order is not found.
     */
    Order getOrderById(Long id) throws OrderNotFoundException, DrinkNotFoundException;

    /**
     * Create a new order.
     *
     * @param newOrder The order to create.
     * @return The order created successfully.
     * @throws DrinkNotFoundException if a drink referenced in the order is not found.
     */
    Order createOrder(InputOrder newOrder) throws DrinkNotFoundException;

    /**
     * Update an existing order.
     *
     * @param id      The ID of the order to update.
     * @param newOrder The updated order information.
     * @return The order updated successfully.
     * @throws OrderNotFoundException if the order is not found.
     * @throws DrinkNotFoundException if a drink referenced in the order is not found.
     */
    Order updateOrder(Long id, InputOrder newOrder) throws OrderNotFoundException, DrinkNotFoundException;

    /**
     * Delete an order by its ID.
     *
     * @param id The ID of the order to delete.
     */
    void deleteOrder(Long id);

}
