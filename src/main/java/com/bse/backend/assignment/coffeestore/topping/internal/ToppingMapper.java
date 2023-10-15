package com.bse.backend.assignment.coffeestore.topping.internal;

import com.bse.backend.assignment.coffeestore.topping.api.model.InputTopping;
import com.bse.backend.assignment.coffeestore.topping.api.model.Topping;
import com.bse.backend.assignment.coffeestore.topping.internal.persistence.ToppingEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ToppingMapper {

    Topping toDto(ToppingEntity entity);
    List<Topping> toDtoList(List<ToppingEntity> entities);
    ToppingEntity toEntity(InputTopping dto);

}
