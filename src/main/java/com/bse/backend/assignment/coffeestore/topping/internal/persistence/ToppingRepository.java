package com.bse.backend.assignment.coffeestore.topping.internal.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToppingRepository extends JpaRepository<ToppingEntity, Long> {
}
