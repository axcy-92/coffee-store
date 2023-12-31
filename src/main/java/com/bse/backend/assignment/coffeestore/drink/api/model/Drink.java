package com.bse.backend.assignment.coffeestore.drink.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A class representing a drink entity for JSON responses.
 * This class is used to serialize drink information into JSON format for responses.
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Drink implements Serializable {

    @Serial
    private static final long serialVersionUID = -6999675392799937238L;

    private Long id;
    private String name;
    private BigDecimal price;

}
