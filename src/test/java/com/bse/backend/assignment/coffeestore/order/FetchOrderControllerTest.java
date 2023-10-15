package com.bse.backend.assignment.coffeestore.order;

import com.bse.backend.assignment.coffeestore.mock.WithMockCustomUser;
import com.bse.backend.assignment.coffeestore.order.api.model.Order;
import com.bse.backend.assignment.coffeestore.order.api.model.OrderDrink;
import com.bse.backend.assignment.coffeestore.order.api.model.OrderItem;
import com.bse.backend.assignment.coffeestore.order.api.model.OrderTopping;
import com.bse.backend.assignment.coffeestore.order.internal.persistence.OrderEntity;
import com.bse.backend.assignment.coffeestore.order.internal.persistence.OrderItemEntity;
import com.bse.backend.assignment.coffeestore.order.internal.persistence.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockCustomUser(username = FetchOrderControllerTest.TEST_USERNAME)
class FetchOrderControllerTest {

    static final String TEST_USERNAME = "fetchOrderControllerTest";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository repository;

    @Test
    @DisplayName("Get all Orders should return a list of Orders with expected details")
    void getAllOrdersTest() throws Exception {
        // given
        OrderEntity entity = prepareOrderEntity();
        Order expectedOrder = prepareOrder(entity);
        OrderItem expectedItem = expectedOrder.getItems().get(0);
        OrderDrink expectedDrink = expectedItem.getDrink();
        OrderTopping expectedTopping = expectedItem.getToppings().get(0);

        // when
        var result = mockMvc.perform(get("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(expectedOrder.getId()), Long.class))
                .andExpect(jsonPath("$[0].username", is(expectedOrder.getUsername())))
                .andExpect(jsonPath("$[0].price", is(expectedOrder.getPrice()), BigDecimal.class))
                .andExpect(jsonPath("$[0].items", hasSize(expectedOrder.getItems().size())))
                .andExpect(jsonPath("$[0].items[0].drink.id", is(expectedDrink.getId()), Long.class))
                .andExpect(jsonPath("$[0].items[0].drink.name", is(expectedDrink.getName())))
                .andExpect(jsonPath("$[0].items[0].drink.price", is(expectedDrink.getPrice()), BigDecimal.class))
                .andExpect(jsonPath("$[0].items[0].toppings", hasSize(expectedItem.getToppings().size())))
                .andExpect(jsonPath("$[0].items[0].toppings[0].id", is(expectedTopping.getId()), Long.class))
                .andExpect(jsonPath("$[0].items[0].toppings[0].name", is(expectedTopping.getName())))
                .andExpect(jsonPath("$[0].items[0].toppings[0].price", is(expectedTopping.getPrice()), BigDecimal.class));
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Get all Orders (Anonymous) should return 403 Forbidden")
    void getAllOrdersAnonymousTest() throws Exception {
        // when
        var result = mockMvc.perform(get("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Get Order by ID should return the expected Order")
    void getOrderByIdTest() throws Exception {
        // given
        OrderEntity entity = prepareOrderEntity();
        Order expectedOrder = prepareOrder(entity);
        OrderItem expectedItem = expectedOrder.getItems().get(0);
        OrderDrink expectedDrink = expectedItem.getDrink();
        OrderTopping expectedTopping = expectedItem.getToppings().get(0);

        // when
        var result = mockMvc.perform(get("/api/v1/orders/" + entity.getId())
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedOrder.getId()), Long.class))
                .andExpect(jsonPath("$.username", is(expectedOrder.getUsername())))
                .andExpect(jsonPath("$.price", is(expectedOrder.getPrice()), BigDecimal.class))
                .andExpect(jsonPath("$.items", hasSize(expectedOrder.getItems().size())))
                .andExpect(jsonPath("$.items[0].drink.id", is(expectedDrink.getId()), Long.class))
                .andExpect(jsonPath("$.items[0].drink.name", is(expectedDrink.getName())))
                .andExpect(jsonPath("$.items[0].drink.price", is(expectedDrink.getPrice()), BigDecimal.class))
                .andExpect(jsonPath("$.items[0].toppings", hasSize(expectedItem.getToppings().size())))
                .andExpect(jsonPath("$.items[0].toppings[0].id", is(expectedTopping.getId()), Long.class))
                .andExpect(jsonPath("$.items[0].toppings[0].name", is(expectedTopping.getName())))
                .andExpect(jsonPath("$.items[0].toppings[0].price", is(expectedTopping.getPrice()), BigDecimal.class));
    }

    @Test
    @DisplayName("Get Order by Non-Existent ID should return 404 Not Found")
    void getOrderByIdNonExistentTest() throws Exception {
        // given
        long orderId = 1L;

        // when
        var result = mockMvc.perform(get("/api/v1/orders/" + orderId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.reason", is(HttpStatus.NOT_FOUND.getReasonPhrase())))
                .andExpect(jsonPath("$.message",
                        is(String.format("No orders found for user %s and id %d", TEST_USERNAME, orderId))));
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Get Order by ID (Anonymous) should return 403 Forbidden")
    void getOrderByIdAnonymousTest() throws Exception {
        // when
        var result = mockMvc.perform(get("/api/v1/orders/1")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isForbidden());
    }

    private OrderEntity prepareOrderEntity() {
        return repository.save(OrderEntity.builder()
                .username(TEST_USERNAME)
                .orderItems(List.of(OrderItemEntity.builder()
                        .drinkId(3L)
                        .toppingIds(List.of(3L))
                        .price(BigDecimal.valueOf(11))
                        .build()))
                .price(BigDecimal.valueOf(11))
                .build());
    }

    private Order prepareOrder(OrderEntity entity) {
        return Order.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .items(List.of(OrderItem.builder()
                        .drink(OrderDrink.builder()
                                .id(entity.getOrderItems().get(0).getDrinkId())
                                .name("Mocha")
                                .price(BigDecimal.valueOf(6.00))
                                .build())
                        .toppings(List.of(OrderTopping.builder()
                                .id(entity.getOrderItems().get(0).getToppingIds().get(0))
                                .name("Chocolate sauce")
                                .price(BigDecimal.valueOf(5.00))
                                .build()))
                        .price(entity.getOrderItems().get(0).getPrice())
                        .build()))
                .price(entity.getPrice())
                .build();
    }
}
