package com.bse.backend.assignment.coffeestore.order.internal;

import com.bse.backend.assignment.coffeestore.drink.api.exception.DrinkNotFoundException;
import com.bse.backend.assignment.coffeestore.order.api.OrderService;
import com.bse.backend.assignment.coffeestore.order.api.exception.OrderNotFoundException;
import com.bse.backend.assignment.coffeestore.order.api.model.InputOrder;
import com.bse.backend.assignment.coffeestore.order.api.model.Order;
import com.bse.backend.assignment.coffeestore.order.internal.discount.DiscountCalculator;
import com.bse.backend.assignment.coffeestore.order.internal.mapper.OrderMapper;
import com.bse.backend.assignment.coffeestore.order.internal.persistence.OrderEntity;
import com.bse.backend.assignment.coffeestore.order.internal.persistence.OrderRepository;
import com.bse.backend.assignment.coffeestore.security.api.UserService;
import com.bse.backend.assignment.coffeestore.security.api.model.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    public static final String AUTHENTICATION_IS_REQUIRED = "Authentication is required";
    public static final String ORDER_NOT_FOUND_TEMPLATE = "No orders found for user %s and id %s";

    private final UserService userService;
    private final DiscountCalculator discountCalculator;
    private final OrderMapper mapper;
    private final OrderRepository repository;

    @Override
    public List<Order> getAllOrders() throws DrinkNotFoundException {
        UserResponse user = userService.getCurrentUser()
                .orElseThrow(() -> new BadCredentialsException(AUTHENTICATION_IS_REQUIRED));
        List<OrderEntity> orderEntities = repository.findAllByUsername(user.getEmail());
        log.debug("Found {} order entities", orderEntities.size());

        return mapper.toDtoList(orderEntities);
    }

    @Override
    public Order getOrderById(Long id) throws OrderNotFoundException, DrinkNotFoundException {
        String username = userService.getCurrentUser()
                .map(UserResponse::getEmail)
                .orElseThrow(() -> new BadCredentialsException(AUTHENTICATION_IS_REQUIRED));

        OrderEntity order = repository.findByUsernameAndId(username, id)
                .orElseThrow(() -> new OrderNotFoundException(String.format(ORDER_NOT_FOUND_TEMPLATE, username, id)));
        log.debug("Found {} order for user {} by id {}", order, username, id);

        return mapper.toDto(order);
    }

    @Override
    public Order createOrder(InputOrder newOrder) throws DrinkNotFoundException {
        UserResponse user = userService.getCurrentUser()
                .orElseThrow(() -> new BadCredentialsException(AUTHENTICATION_IS_REQUIRED));
        OrderEntity entity = mapper.toEntity(user, newOrder);

        applyDiscount(entity);
        OrderEntity savedOrder = repository.save(entity);
        log.debug("New Order has been successfully created: {}", savedOrder);

        return mapper.toDto(savedOrder);
    }

    @Override
    public Order updateOrder(Long id, InputOrder order) throws OrderNotFoundException, DrinkNotFoundException {
        UserResponse user = userService.getCurrentUser()
                .orElseThrow(() -> new BadCredentialsException(AUTHENTICATION_IS_REQUIRED));
        String username = user.getEmail();
        OrderEntity entity = repository.findByUsernameAndId(username, id)
                .orElseThrow(() -> new OrderNotFoundException(String.format(ORDER_NOT_FOUND_TEMPLATE, username, id)));
        log.debug("Update order {} for user {} with values {}", entity, username, order);

        if (order == null) return mapper.toDto(entity);

        OrderEntity updatedEntity = mapper.toEntity(user, order);
        updatedEntity.setId(id);
        applyDiscount(entity);
        updatedEntity = repository.save(updatedEntity);
        log.debug("Order has been successfully updated: {}", updatedEntity);

        return mapper.toDto(updatedEntity);
    }

    @Override
    public void deleteOrder(Long id) {
        String username = userService.getCurrentUser()
                .map(UserResponse::getEmail)
                .orElseThrow(() -> new BadCredentialsException(AUTHENTICATION_IS_REQUIRED));

        repository.deleteByUsernameAndId(username, id);
        log.debug("Order with id {} has been deleted for user {}", id, username);
    }

    private void applyDiscount(OrderEntity entity) {
        BigDecimal discount = discountCalculator.calculateDiscount(entity);
        if (discount.signum() > 0) {
            entity.setOriginalPrice(entity.getPrice());
            entity.setDiscount(discount);
            entity.setPrice(entity.getPrice().subtract(discount));
        }
    }
}
