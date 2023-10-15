package com.bse.backend.assignment.coffeestore.order.internal.discount.rule;

import com.bse.backend.assignment.coffeestore.order.internal.persistence.OrderEntity;
import com.bse.backend.assignment.coffeestore.order.internal.persistence.OrderItemEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@SuppressWarnings("unused")
public class DiscountRuleFreeItem implements DiscountRule {

    @Value("${coffee-store.discount.rule-free-item.min-items}")
    private int minItems;

    @Override
    public BigDecimal apply(OrderEntity order) {
        if (order == null || order.getOrderItems() == null) return BigDecimal.ZERO;

        List<OrderItemEntity> orderItems = order.getOrderItems();
        if (orderItems.size() < minItems) return BigDecimal.ZERO;

        Optional<OrderItemEntity> minPriceItem = orderItems.stream()
                .min(Comparator.comparing(OrderItemEntity::getPrice));
        if (minPriceItem.isEmpty()) return BigDecimal.ZERO;

        // The discount is equal to the price of the item with the lowest price
        BigDecimal discount = minPriceItem.get().getPrice();

        log.debug("Calculated free item discount amount: {} (free item: {})", discount, minPriceItem.get());
        return discount;
    }
}
