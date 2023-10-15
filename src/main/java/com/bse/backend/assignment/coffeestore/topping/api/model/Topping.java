package com.bse.backend.assignment.coffeestore.topping.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A class representing a topping entity for JSON responses.
 * This class is used to serialize topping information into JSON format for responses.
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Topping implements Serializable {

    @Serial
    private static final long serialVersionUID = -1759073168640464747L;

    private Long id;
    private String name;
    private BigDecimal price;

}
