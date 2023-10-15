package com.bse.backend.assignment.coffeestore.order.internal.discount.rule;

import com.bse.backend.assignment.coffeestore.order.internal.persistence.OrderEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
@SuppressWarnings("unused")
public class DiscountRule25Percent implements DiscountRule {

    @Value("${coffee-store.discount.rule-percent.threshold}")
    private BigDecimal threshold;

    @Value("${coffee-store.discount.rule-percent.discount-percent}")
    private BigDecimal discountPercent;

    @Override
    public BigDecimal apply(OrderEntity order) {
        if (order == null || order.getPrice() == null) return BigDecimal.ZERO;

        BigDecimal price = order.getPrice();
        if (price.compareTo(threshold) <= 0) return BigDecimal.ZERO;

        BigDecimal discount = price.multiply(discountPercent);

        log.debug("Calculated 25% discount amount: {}", discount);
        return discount;
    }

}
