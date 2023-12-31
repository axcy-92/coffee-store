package com.bse.backend.assignment.coffeestore.order.internal.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findAllByUsername(String username);
    Optional<OrderEntity> findByUsernameAndId(String username, Long id);
    void deleteByUsernameAndId(String username, Long id);

}
