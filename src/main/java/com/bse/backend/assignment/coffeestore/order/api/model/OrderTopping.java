package com.bse.backend.assignment.coffeestore.order.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Represents a topping for a drink in an order.
 * This class is serializable and used for JSON responses.
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class OrderTopping implements Serializable {

    @Serial
    private static final long serialVersionUID = -3649472517879595061L;

    private Long id;
    private String name;
    private BigDecimal price;

}
