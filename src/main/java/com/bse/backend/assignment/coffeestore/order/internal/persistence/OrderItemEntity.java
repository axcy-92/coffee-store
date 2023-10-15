package com.bse.backend.assignment.coffeestore.order.internal.persistence;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
@Entity
@EqualsAndHashCode(exclude = { "id", "order" })
@NoArgsConstructor
@Table(name = "order_items")
@ToString(exclude = "order")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private OrderEntity order;

    private Long drinkId;

    @ElementCollection
    private List<Long> toppingIds;

    private BigDecimal price;
}
