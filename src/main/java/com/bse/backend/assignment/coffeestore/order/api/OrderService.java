package com.bse.backend.assignment.coffeestore.order.api;

import com.bse.backend.assignment.coffeestore.drink.api.exception.DrinkNotFoundException;
import com.bse.backend.assignment.coffeestore.order.api.exception.OrderNotFoundException;
import com.bse.backend.assignment.coffeestore.order.api.model.InputOrder;
import com.bse.backend.assignment.coffeestore.order.api.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders() throws DrinkNotFoundException;
    Order getOrderById(Long id) throws OrderNotFoundException, DrinkNotFoundException;
    Order createOrder(InputOrder newOrder) throws DrinkNotFoundException;
    Order updateOrder(Long id, InputOrder newOrder) throws OrderNotFoundException, DrinkNotFoundException;
    void deleteOrder(Long id);
}
