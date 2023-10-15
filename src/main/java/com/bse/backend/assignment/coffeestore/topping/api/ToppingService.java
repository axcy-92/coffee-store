package com.bse.backend.assignment.coffeestore.topping.api;

import com.bse.backend.assignment.coffeestore.topping.api.exception.ToppingNotFoundException;
import com.bse.backend.assignment.coffeestore.topping.api.model.InputTopping;
import com.bse.backend.assignment.coffeestore.topping.api.model.Topping;

import java.util.List;

/**
 * This interface defines the service methods for managing toppings in the Coffee Store application.
 * It provides methods to retrieve, create, update, and delete toppings.
 */
public interface ToppingService {

    /**
     * Retrieves a list of all available toppings.
     *
     * @return A list of {@link Topping} objects representing the available toppings.
     */
    List<Topping> getAllToppings();

    /**
     * Retrieves a specific topping by its unique identifier.
     *
     * @param id The unique identifier of the topping.
     * @return The {@link Topping} object representing the retrieved topping.
     *
     * @throws ToppingNotFoundException if the specified topping is not found.
     */
    Topping getToppingById(Long id) throws ToppingNotFoundException;

    /**
     * Retrieves a list of toppings based on a list of unique identifiers.
     *
     * @param ids A list of unique identifiers for the toppings to be retrieved.
     * @return A list of {@link Topping} objects representing the retrieved toppings.
     */
    List<Topping> getAllToppingsById(List<Long> ids);

    /**
     * Creates a new topping based on the provided information.
     *
     * @param topping The {@link InputTopping} object containing the details of the new topping.
     * @return The {@link Topping} object representing the newly created topping.
     */
    Topping createTopping(InputTopping topping);

    /**
     * Updates an existing topping based on the provided information.
     *
     * @param id          The unique identifier of the topping to be updated.
     * @param updatedTopping The {@link InputTopping} object containing the updated details of the topping.
     * @return The {@link Topping} object representing the updated topping.
     *
     * @throws ToppingNotFoundException if the specified topping is not found.
     */
    Topping updateTopping(Long id, InputTopping updatedTopping) throws ToppingNotFoundException;

    /**
     * Deletes a topping by its unique identifier.
     *
     * @param id The unique identifier of the topping to be deleted.
     */
    void deleteTopping(Long id);

}
