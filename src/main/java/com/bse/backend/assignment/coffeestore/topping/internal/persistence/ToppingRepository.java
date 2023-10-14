package com.bse.backend.assignment.coffeestore.topping.internal.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ToppingRepository extends JpaRepository<ToppingEntity, Long> {
}
