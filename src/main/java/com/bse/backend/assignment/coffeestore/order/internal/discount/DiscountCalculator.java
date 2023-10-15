package com.bse.backend.assignment.coffeestore.order.internal.discount;

import com.bse.backend.assignment.coffeestore.order.internal.discount.rule.DiscountRule;
import com.bse.backend.assignment.coffeestore.order.internal.persistence.OrderEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DiscountCalculator {

    private final List<DiscountRule> discountRules;

    public BigDecimal calculateDiscount(OrderEntity order) {
        BigDecimal effectiveDiscount = BigDecimal.ZERO;
        String effectiveRuleName = null;
        BigDecimal lowestCartAmount = order.getPrice();

        for (DiscountRule rule : discountRules) {
            BigDecimal discount = rule.apply(order);
            BigDecimal discountedPrice = order.getPrice().subtract(discount);

            if (discountedPrice.compareTo(lowestCartAmount) < 0) {
                effectiveDiscount = discount;
                effectiveRuleName = rule.getClass().getName();
                lowestCartAmount = discountedPrice;
            }
        }

        log.debug("Effective discount: {} (rule: {})", effectiveDiscount, effectiveRuleName);
        return effectiveDiscount;
    }

}
