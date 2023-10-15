package com.bse.backend.assignment.coffeestore.drink.api;

import com.bse.backend.assignment.coffeestore.drink.api.exception.DrinkNotFoundException;
import com.bse.backend.assignment.coffeestore.drink.api.model.Drink;
import com.bse.backend.assignment.coffeestore.drink.api.model.InputDrink;

import java.util.List;

public interface DrinkService {
    List<Drink> getAllDrinks();
    Drink getDrinkById(Long id) throws DrinkNotFoundException;
    Drink createDrink(InputDrink topping);
    Drink updateDrink(Long id, InputDrink updatedDrink) throws DrinkNotFoundException;
    void deleteDrink(Long id);
}
