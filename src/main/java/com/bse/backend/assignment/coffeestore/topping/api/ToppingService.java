package com.bse.backend.assignment.coffeestore.topping.api;

import com.bse.backend.assignment.coffeestore.topping.api.model.InputTopping;
import com.bse.backend.assignment.coffeestore.topping.api.model.Topping;

import java.util.List;

public interface ToppingService {
    List<Topping> getAllToppings();
    Topping getToppingById(Long id);
    Topping createTopping(InputTopping topping);
    Topping updateTopping(Long id, InputTopping updatedTopping);
    void deleteTopping(Long id);
}
