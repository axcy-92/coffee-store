package com.bse.backend.assignment.coffeestore.drink.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

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
