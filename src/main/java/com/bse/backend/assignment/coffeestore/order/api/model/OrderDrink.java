package com.bse.backend.assignment.coffeestore.order.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Represents a drink included in an order with its details. This class is serializable and used for JSON responses.
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class OrderDrink implements Serializable {

    @Serial
    private static final long serialVersionUID = -4912698594594204964L;

    private Long id;
    private String name;
    private BigDecimal price;

}
