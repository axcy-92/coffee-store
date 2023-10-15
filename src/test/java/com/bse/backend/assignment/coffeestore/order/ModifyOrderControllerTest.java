package com.bse.backend.assignment.coffeestore.order;

import com.bse.backend.assignment.coffeestore.drink.api.model.InputDrink;
import com.bse.backend.assignment.coffeestore.mock.WithMockCustomUser;
import com.bse.backend.assignment.coffeestore.order.api.model.InputOrder;
import com.bse.backend.assignment.coffeestore.order.api.model.InputOrderItem;
import com.bse.backend.assignment.coffeestore.order.api.model.OrderDrink;
import com.bse.backend.assignment.coffeestore.order.api.model.OrderTopping;
import com.bse.backend.assignment.coffeestore.order.internal.persistence.OrderEntity;
import com.bse.backend.assignment.coffeestore.order.internal.persistence.OrderItemEntity;
import com.bse.backend.assignment.coffeestore.order.internal.persistence.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockCustomUser(username = ModifyOrderControllerTest.TEST_USERNAME)
class ModifyOrderControllerTest {

    static final String TEST_USERNAME = "modifyOrderControllerTest";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderRepository repository;

    @Test
    @DisplayName("Create new Order should return new Order")
    void createNewOrderTest() throws Exception {
        // given
        var expectedDrink = OrderDrink.builder()
                .id(1L)
                .name("Black Coffee")
                .price(BigDecimal.valueOf(4.00))
                .build();
        var expectedTopping = OrderTopping.builder()
                .id(2L)
                .name("Hazelnut syrup")
                .price(BigDecimal.valueOf(3.00))
                .build();
        var expectedPrice = expectedDrink.getPrice().add(expectedTopping.getPrice());

        var inputOrder = InputOrder.builder()
                .items(List.of(InputOrderItem.builder()
                        .drinkId(expectedDrink.getId())
                        .toppingIds(List.of(expectedTopping.getId()))
                        .build()))
                .build();

        // when
        var result = mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputOrder)));

        // then
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.username", is(TEST_USERNAME)))
                .andExpect(jsonPath("$.price", is(expectedPrice), BigDecimal.class))
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].drink.id", is(expectedDrink.getId()), Long.class))
                .andExpect(jsonPath("$.items[0].drink.name", is(expectedDrink.getName())))
                .andExpect(jsonPath("$.items[0].drink.price", is(expectedDrink.getPrice()), BigDecimal.class))
                .andExpect(jsonPath("$.items[0].toppings", hasSize(1)))
                .andExpect(jsonPath("$.items[0].toppings[0].id", is(expectedTopping.getId()), Long.class))
                .andExpect(jsonPath("$.items[0].toppings[0].name", is(expectedTopping.getName())))
                .andExpect(jsonPath("$.items[0].toppings[0].price", is(expectedTopping.getPrice()), BigDecimal.class));
    }

    @Test
    @DisplayName("Create new Order with invalid data should return 400 Bad Request")
    void createNewOrderInvalidDataTest() throws Exception {
        // given
        var inputToppingIds = new ArrayList<Long>();
        inputToppingIds.add(null);
        inputToppingIds.add(0L);
        var inputOrder = InputOrder.builder()
                .items(List.of(InputOrderItem.builder()
                        .toppingIds(inputToppingIds)
                        .build()))
                .build();

        // when
        var result = mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputOrder)));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.reason", is(HttpStatus.BAD_REQUEST.getReasonPhrase())))
                .andExpect(jsonPath("$.errors..fieldName",
                        hasItems("items[0].drinkId", "items[0].toppingIds[0]", "items[0].toppingIds[1]")))
                .andExpect(jsonPath("$.errors..errorMessage",
                        hasItems("must not be null", "must be greater than 0")));
    }

    @Test
    @DisplayName("Create new Order with Non-Existent Drink should return 404 Not Found")
    void createNewOrderNonExistentDrinkTest() throws Exception {
        // given
        var inputOrder = InputOrder.builder()
                .items(List.of(InputOrderItem.builder()
                        .drinkId(999L)
                        .build()))
                .build();

        // when
        var result = mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputOrder)));

        // then
        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.reason", is(HttpStatus.NOT_FOUND.getReasonPhrase())))
                .andExpect(jsonPath("$.message", is("Drink not found")));
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Create new Order (Anonymous) should return 403 Forbidden")
    void createNewOrderAnonymousTest() throws Exception {
        // given
        var inputOrder = InputOrder.builder()
                .items(List.of(InputOrderItem.builder()
                        .drinkId(1L)
                        .build()))
                .build();

        // when
        var result = mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputOrder)));

        // then
        result.andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Update Order should return updated Order")
    void updateOrderTest() throws Exception {
        // given
        var expectedDrink = OrderDrink.builder()
                .id(1L)
                .name("Black Coffee")
                .price(BigDecimal.valueOf(4.00))
                .build();
        var expectedToppings = List.of(
                OrderTopping.builder()
                        .id(1L)
                        .name("Milk")
                        .price(BigDecimal.valueOf(2.00))
                        .build(),
                OrderTopping.builder()
                        .id(2L)
                        .name("Hazelnut syrup")
                        .price(BigDecimal.valueOf(3.00))
                        .build(),
                OrderTopping.builder()
                        .id(3L)
                        .name("Chocolate sauce")
                        .price(BigDecimal.valueOf(5.00))
                        .build()
        );

        var orderItemEntities = new ArrayList<OrderItemEntity>();
        orderItemEntities.add(OrderItemEntity.builder()
                .drinkId(expectedDrink.getId())
                .toppingIds(expectedToppings.stream()
                        .skip(1)
                        .map(OrderTopping::getId)
                        .toList())
                .price(BigDecimal.TEN)
                .build());

        var entity = OrderEntity.builder()
                .username(TEST_USERNAME)
                .orderItems(orderItemEntities)
                .price(BigDecimal.TEN)
                .build();
        entity = repository.save(entity);

        var inputOrder = InputOrder.builder()
                .items(List.of(InputOrderItem.builder()
                        .drinkId(expectedDrink.getId())
                        .toppingIds(expectedToppings.stream()
                                .map(OrderTopping::getId)
                                .toList())
                        .build()))
                .build();

        // when
        var result = mockMvc.perform(put("/api/v1/orders/" + entity.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputOrder)));

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(entity.getId()), Long.class))
                .andExpect(jsonPath("$.username", is(entity.getUsername())))
                .andExpect(jsonPath("$.price", is(14.00)))
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].drink.id", is(expectedDrink.getId()), Long.class))
                .andExpect(jsonPath("$.items[0].drink.name", is(expectedDrink.getName())))
                .andExpect(jsonPath("$.items[0].drink.price", is(expectedDrink.getPrice()), BigDecimal.class))
                .andExpect(jsonPath("$.items[0].toppings", hasSize(expectedToppings.size())))
                .andExpect(jsonPath("$.items[0].toppings[0].id", is(expectedToppings.get(0).getId()), Long.class))
                .andExpect(jsonPath("$.items[0].toppings[1].id", is(expectedToppings.get(1).getId()), Long.class))
                .andExpect(jsonPath("$.items[0].toppings[2].id", is(expectedToppings.get(2).getId()), Long.class))
                .andExpect(jsonPath("$.items[0].toppings[0].name", is(expectedToppings.get(0).getName())))
                .andExpect(jsonPath("$.items[0].toppings[1].name", is(expectedToppings.get(1).getName())))
                .andExpect(jsonPath("$.items[0].toppings[2].name", is(expectedToppings.get(2).getName())))
                .andExpect(jsonPath("$.items[0].toppings[0].price",
                        is(expectedToppings.get(0).getPrice()), BigDecimal.class))
                .andExpect(jsonPath("$.items[0].toppings[1].price",
                        is(expectedToppings.get(1).getPrice()), BigDecimal.class))
                .andExpect(jsonPath("$.items[0].toppings[2].price",
                        is(expectedToppings.get(2).getPrice()), BigDecimal.class));
    }

    @Test
    @DisplayName("Update Order with Non-Existent ID should return 404 Not Found")
    void updateOrderNonExistentTest() throws Exception {
        // given
        long orderId = 1L;
        var inputOrder = InputOrder.builder()
                .items(List.of(InputOrderItem.builder()
                        .drinkId(1L)
                        .toppingIds(List.of(1L, 2L))
                        .build()))
                .build();

        // when
        var result = mockMvc.perform(put("/api/v1/orders/" + orderId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputOrder)));

        // then
        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.reason", is(HttpStatus.NOT_FOUND.getReasonPhrase())))
                .andExpect(jsonPath("$.message",
                        is(String.format("No orders found for user %s and id %d", TEST_USERNAME, orderId))));
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Update Order (Anonymous) should return 403 Forbidden")
    void updateOrderAnonymousTest() throws Exception {
        // given
        long drinkId = 1L;
        var updatedDrink = InputDrink.builder()
                .name("Anonymous Drink")
                .price(BigDecimal.TEN)
                .build();

        // when
        var result = mockMvc.perform(put("/api/v1/drinks/" + drinkId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDrink)));

        // then
        result.andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Delete Order should remove the Order")
    void deleteOrderTest() throws Exception {
        // given
        var entity = OrderEntity.builder()
                .username(TEST_USERNAME)
                .orderItems(List.of(OrderItemEntity.builder()
                        .drinkId(1L)
                        .toppingIds(List.of(1L))
                        .price(BigDecimal.valueOf(9))
                        .build()))
                .price(BigDecimal.valueOf(9))
                .build();
        entity = repository.save(entity);

        // when
        var result = mockMvc.perform(delete("/api/v1/orders/" + entity.getId())
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNoContent());
        assertTrue(repository.findById(entity.getId()).isEmpty());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Delete Order (Anonymous) should return 403 Forbidden")
    void deleteOrderAnonymousTest() throws Exception {
        // given
        long orderId = 1L;

        // when
        var result = mockMvc.perform(delete("/api/v1/orders/" + orderId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isForbidden());
    }
}
