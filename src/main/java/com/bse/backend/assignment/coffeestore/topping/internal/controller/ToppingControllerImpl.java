package com.bse.backend.assignment.coffeestore.topping.internal.controller;

import com.bse.backend.assignment.coffeestore.topping.api.ToppingService;
import com.bse.backend.assignment.coffeestore.topping.api.ToppingController;
import com.bse.backend.assignment.coffeestore.topping.api.model.InputTopping;
import com.bse.backend.assignment.coffeestore.topping.api.model.Topping;
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
@RequestMapping("/api/v1/toppings")
@RequiredArgsConstructor
public class ToppingControllerImpl implements ToppingController {

    private final ToppingService service;

    @Override
    @GetMapping
    public ResponseEntity<List<Topping>> getAllToppings() {
        List<Topping> toppings = service.getAllToppings();

        return new ResponseEntity<>(toppings, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Topping> getToppingById(@PathVariable Long id) {
        Topping topping = service.getToppingById(id);

        return new ResponseEntity<>(topping, HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Topping> createTopping(@RequestBody InputTopping newTopping) {
        Topping topping = service.createTopping(newTopping);

        return new ResponseEntity<>(topping, HttpStatus.CREATED);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Topping> updateTopping(@PathVariable Long id, @RequestBody InputTopping newTopping) {
        Topping topping = service.updateTopping(id, newTopping);

        return new ResponseEntity<>(topping, HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopping(@PathVariable Long id) {
        service.deleteTopping(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
