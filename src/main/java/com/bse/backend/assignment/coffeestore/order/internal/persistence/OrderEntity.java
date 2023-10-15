package com.bse.backend.assignment.coffeestore.order.internal.persistence;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
@Entity
@EqualsAndHashCode(exclude = { "id" })
@NoArgsConstructor
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private BigDecimal originalPrice;
    private BigDecimal discount;
    private BigDecimal price;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItemEntity> orderItems;

}
