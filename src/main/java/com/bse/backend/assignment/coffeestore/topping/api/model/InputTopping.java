package com.bse.backend.assignment.coffeestore.topping.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class InputTopping implements Serializable {

    @Serial
    private static final long serialVersionUID = 5802568915882915860L;

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private BigDecimal price;
}