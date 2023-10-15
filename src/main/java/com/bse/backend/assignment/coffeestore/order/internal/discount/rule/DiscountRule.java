package com.bse.backend.assignment.coffeestore.order.internal.discount.rule;

import com.bse.backend.assignment.coffeestore.order.internal.persistence.OrderEntity;

import java.math.BigDecimal;

public interface DiscountRule {

    BigDecimal apply(OrderEntity order);

}
