package com.bse.backend.assignment.coffeestore.order.internal;

import com.bse.backend.assignment.coffeestore.common.exception.NotFoundException;
import com.bse.backend.assignment.coffeestore.order.api.OrderController;
import com.bse.backend.assignment.coffeestore.order.api.OrderService;
import com.bse.backend.assignment.coffeestore.order.api.model.InputOrder;
import com.bse.backend.assignment.coffeestore.order.api.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService service;

    @Override
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() throws NotFoundException {
        List<Order> orders = service.getAllOrders();

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) throws NotFoundException {
        Order order = service.getOrderById(id);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @Override
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody InputOrder inputOrder) throws NotFoundException {
        Order order = service.createOrder(inputOrder);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(
            @PathVariable Long id,
            @RequestBody InputOrder inputOrder
    ) throws NotFoundException {
        Order order = service.updateOrder(id, inputOrder);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        service.deleteOrder(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
