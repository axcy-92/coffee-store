package com.bse.backend.assignment.coffeestore.order.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = -5499983510531373791L;

    private Long id;
    private String username;
    private BigDecimal originalPrice;
    private BigDecimal discount;
    private BigDecimal price;
    private List<OrderItem> items;
}
