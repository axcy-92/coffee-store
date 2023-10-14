package com.bse.backend.assignment.coffeestore.drink.internal.controller;

import com.bse.backend.assignment.coffeestore.drink.api.DrinkController;
import com.bse.backend.assignment.coffeestore.drink.api.DrinkService;
import com.bse.backend.assignment.coffeestore.drink.api.model.Drink;
import com.bse.backend.assignment.coffeestore.drink.api.model.InputDrink;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/drinks")
@RequiredArgsConstructor
public class DrinksControllerImpl implements DrinkController {

    private final DrinkService service;

    @Override
    @GetMapping
    public ResponseEntity<List<Drink>> getAllDrinks() {
        List<Drink> drinks = service.getAllDrinks();

        return new ResponseEntity<>(drinks, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Drink> getDrinkById(@PathVariable Long id) {
        Drink drink = service.getDrinkById(id);

        return new ResponseEntity<>(drink, HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Drink> createDrink(@RequestBody InputDrink newDrink) {
        Drink drink = service.createDrink(newDrink);

        return new ResponseEntity<>(drink, HttpStatus.CREATED);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Drink> updateDrink(@PathVariable Long id, @RequestBody InputDrink newDrink) {
        Drink drink = service.updateDrink(id, newDrink);

        return new ResponseEntity<>(drink, HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrink(@PathVariable Long id) {
        service.deleteDrink(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
