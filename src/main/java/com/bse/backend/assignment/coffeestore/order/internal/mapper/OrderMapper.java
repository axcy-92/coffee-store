package com.bse.backend.assignment.coffeestore.order.internal.mapper;

import com.bse.backend.assignment.coffeestore.drink.api.exception.DrinkNotFoundException;
import com.bse.backend.assignment.coffeestore.order.api.model.InputOrder;
import com.bse.backend.assignment.coffeestore.order.api.model.Order;
import com.bse.backend.assignment.coffeestore.order.internal.persistence.OrderEntity;
import com.bse.backend.assignment.coffeestore.order.internal.persistence.OrderItemEntity;
import com.bse.backend.assignment.coffeestore.security.api.model.UserResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;
import java.util.List;

@Mapper(uses = OrderItemMapper.class, builder = @Builder(disableBuilder = true))
public interface OrderMapper {

    @Mapping(target = "items", source = "orderItems")
    Order toDto(OrderEntity entity) throws DrinkNotFoundException;

    List<Order> toDtoList(List<OrderEntity> entityList) throws DrinkNotFoundException;

    @Mapping(target = "username", source = "user.email")
    @Mapping(target = "orderItems", source = "dto.items")
    OrderEntity toEntity(UserResponse user, InputOrder dto) throws DrinkNotFoundException;

    @AfterMapping
    default void afterMappingToEntity(@MappingTarget OrderEntity orderEntity) {
        if (orderEntity == null) return;

        BigDecimal totalCartAmount = BigDecimal.ZERO;
        for (OrderItemEntity orderItemEntity : orderEntity.getOrderItems()) {
            orderItemEntity.setOrder(orderEntity);
            totalCartAmount = totalCartAmount.add(orderItemEntity.getPrice());
        }

        orderEntity.setPrice(totalCartAmount);
    }

}
