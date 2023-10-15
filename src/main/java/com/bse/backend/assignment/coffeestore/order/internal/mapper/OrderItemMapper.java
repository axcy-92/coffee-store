package com.bse.backend.assignment.coffeestore.order.internal.mapper;

import com.bse.backend.assignment.coffeestore.drink.api.DrinkService;
import com.bse.backend.assignment.coffeestore.drink.api.exception.DrinkNotFoundException;
import com.bse.backend.assignment.coffeestore.drink.api.model.Drink;
import com.bse.backend.assignment.coffeestore.order.api.model.InputOrderItem;
import com.bse.backend.assignment.coffeestore.order.api.model.OrderDrink;
import com.bse.backend.assignment.coffeestore.order.api.model.OrderItem;
import com.bse.backend.assignment.coffeestore.order.api.model.OrderTopping;
import com.bse.backend.assignment.coffeestore.order.internal.persistence.OrderItemEntity;
import com.bse.backend.assignment.coffeestore.topping.api.ToppingService;
import com.bse.backend.assignment.coffeestore.topping.api.model.Topping;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

@Mapper(builder = @Builder(disableBuilder = true))
public abstract class OrderItemMapper {

    @Autowired
    private DrinkService drinkService;

    @Autowired
    private ToppingService toppingService;

    @Mapping(target = "drink", source = "drinkId")
    @Mapping(target = "toppings", source = "toppingIds")
    public abstract OrderItem toDto(OrderItemEntity entity) throws DrinkNotFoundException;

    public abstract List<OrderItem> toDtoList(List<OrderItemEntity> entityList) throws DrinkNotFoundException;

    public abstract OrderItemEntity toEntity(InputOrderItem dto) throws DrinkNotFoundException;

    public abstract List<OrderItemEntity> toEntityList(List<InputOrderItem> dtoList) throws DrinkNotFoundException;

    @AfterMapping
    protected void attachPrice(@MappingTarget OrderItemEntity entity) throws DrinkNotFoundException {
        if (entity == null) return;

        BigDecimal drinkPrice = drinkService.getDrinkById(entity.getDrinkId()).getPrice();
        BigDecimal toppingsPrice = toppingService.getAllToppingsById(entity.getToppingIds())
                .stream()
                .map(Topping::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        entity.setPrice(drinkPrice.add(toppingsPrice));
    }

    protected OrderDrink toOrderDrink(Long id) throws DrinkNotFoundException {
        Drink drink = drinkService.getDrinkById(id);

        return OrderDrink.builder()
                .id(drink.getId())
                .name(drink.getName())
                .price(drink.getPrice())
                .build();
    }

    protected List<OrderTopping> toOrderToppingList(List<Long> ids) {
        return toppingService.getAllToppingsById(ids)
                .stream()
                .map(topping -> OrderTopping.builder()
                        .id(topping.getId())
                        .name(topping.getName())
                        .price(topping.getPrice())
                        .build())
                .toList();
    }

}
