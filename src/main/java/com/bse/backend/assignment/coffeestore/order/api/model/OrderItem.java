package com.bse.backend.assignment.coffeestore.order.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Represents an item in an order, consisting of a drink and optional toppings, along with its price.
 * This class is serializable and used for JSON responses.
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class OrderItem implements Serializable {

    @Serial
    private static final long serialVersionUID = -7427043553054498718L;

    private OrderDrink drink;
    private List<OrderTopping> toppings;
    private BigDecimal price;

}
