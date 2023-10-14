package com.bse.backend.assignment.coffeestore.drink.internal;

import com.bse.backend.assignment.coffeestore.drink.api.model.Drink;
import com.bse.backend.assignment.coffeestore.drink.api.model.InputDrink;
import com.bse.backend.assignment.coffeestore.drink.internal.persistence.DrinkEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface DrinkMapper {

    Drink toDto(DrinkEntity entity);

    List<Drink> toDtoList(List<DrinkEntity> entities);

    DrinkEntity toEntity(InputDrink dto);

}
