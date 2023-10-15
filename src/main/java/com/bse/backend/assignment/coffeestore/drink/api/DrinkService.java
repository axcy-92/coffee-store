package com.bse.backend.assignment.coffeestore.drink.api;

import com.bse.backend.assignment.coffeestore.drink.api.exception.DrinkNotFoundException;
import com.bse.backend.assignment.coffeestore.drink.api.model.Drink;
import com.bse.backend.assignment.coffeestore.drink.api.model.InputDrink;

import java.util.List;

/**
 * A service interface for managing drinks.
 * This interface defines methods for retrieving, creating, updating, and deleting drinks.
 */
public interface DrinkService {

    /**
     * Retrieves a list of all available drinks.
     *
     * @return A list of drinks.
     */
    List<Drink> getAllDrinks();

    /**
     * Retrieves a specific drink by its unique identifier.
     *
     * @param id The unique identifier of the drink.
     * @return The drink with the specified ID.
     * @throws DrinkNotFoundException if the requested drink does not exist.
     */
    Drink getDrinkById(Long id) throws DrinkNotFoundException;

    /**
     * Creates a new drink based on the provided input data.
     *
     * @param drink The input data for creating a new drink.
     * @return The newly created drink.
     */
    Drink createDrink(InputDrink drink);

    /**
     * Updates an existing drink with new information.
     *
     * @param id The unique identifier of the drink to be updated.
     * @param drink The updated drink information.
     * @return The updated drink.
     * @throws DrinkNotFoundException if the specified drink does not exist.
     */
    Drink updateDrink(Long id, InputDrink drink) throws DrinkNotFoundException;

    /**
     * Deletes a drink by its unique identifier.
     *
     * @param id The unique identifier of the drink to be deleted.
     */
    void deleteDrink(Long id);

}
