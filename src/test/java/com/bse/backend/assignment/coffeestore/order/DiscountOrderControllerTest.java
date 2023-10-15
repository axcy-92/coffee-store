package com.bse.backend.assignment.coffeestore.order;

import com.bse.backend.assignment.coffeestore.mock.WithMockCustomUser;
import com.bse.backend.assignment.coffeestore.order.api.model.InputOrder;
import com.bse.backend.assignment.coffeestore.order.api.model.InputOrderItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockCustomUser(username = DiscountOrderControllerTest.TEST_USERNAME)
class DiscountOrderControllerTest {

    static final String TEST_USERNAME = "discountOrderControllerTest";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Create new order with 25% promotion should make 25% discount")
    void createNewOrderWith25PercentDiscountTest() throws Exception {
        // given
        var drinkPrice = BigDecimal.valueOf(4.00);
        var toppingsPrice = BigDecimal.TEN;

        var expectedOriginalPrice = drinkPrice.add(toppingsPrice);
        var expectedDiscount = expectedOriginalPrice.multiply(BigDecimal.valueOf(0.25));
        var expectedPrice = expectedOriginalPrice.subtract(expectedDiscount);

        var inputOrder = InputOrder.builder()
                .items(List.of(InputOrderItem.builder()
                        .drinkId(1L)
                        .toppingIds(List.of(1L, 2L, 3L))
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
                .andExpect(jsonPath("$.originalPrice", is(expectedOriginalPrice), BigDecimal.class))
                .andExpect(jsonPath("$.discount", comparesEqualTo(expectedDiscount), BigDecimal.class))
                .andExpect(jsonPath("$.price", comparesEqualTo(expectedPrice), BigDecimal.class));
    }

    @Test
    @DisplayName("Create new order with free item promotion should make free one item with the lowest amount")
    void createNewOrderWithFreeItemDiscountTest() throws Exception {
        // given
        var expectedOriginalPrice = BigDecimal.valueOf(10.0);
        var expectedDiscount = BigDecimal.valueOf(3);
        var expectedPrice = expectedOriginalPrice.subtract(expectedDiscount);

        var inputOrder = InputOrder.builder()
                .items(List.of(
                        InputOrderItem.builder()
                                .drinkId(1L)
                                .build(),
                        InputOrderItem.builder()
                                .drinkId(4L)
                                .build(),
                        InputOrderItem.builder()
                                .drinkId(4L)
                                .build()
                )).build();

        // when
        var result = mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputOrder)));

        // then
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.username", is(TEST_USERNAME)))
                .andExpect(jsonPath("$.originalPrice", is(expectedOriginalPrice), BigDecimal.class))
                .andExpect(jsonPath("$.discount", comparesEqualTo(expectedDiscount), BigDecimal.class))
                .andExpect(jsonPath("$.price", comparesEqualTo(expectedPrice), BigDecimal.class));
    }

    @Test
    @DisplayName("Create new order with both promotions should use the promotion with the lowest cart amount")
    void createNewOrderWithLowestCardAmountDiscountTest() throws Exception {
        var drinksPrice = BigDecimal.valueOf(15.0);
        var toppingsPrice = BigDecimal.valueOf(10.0);

        var expectedOriginalPrice = drinksPrice.add(toppingsPrice);
        var expectedDiscount = BigDecimal.valueOf(6.25);
        var expectedPrice = expectedOriginalPrice.subtract(expectedDiscount);

        var inputOrder = InputOrder.builder()
                .items(List.of(
                        InputOrderItem.builder()
                                .drinkId(1L)
                                .toppingIds(List.of(1L))
                                .build(),
                        InputOrderItem.builder()
                                .drinkId(2L)
                                .toppingIds(List.of(2L))
                                .build(),
                        InputOrderItem.builder()
                                .drinkId(3L)
                                .toppingIds(List.of(3L))
                                .build()
                )).build();

        // when
        var result = mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputOrder)));

        // then
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.username", is(TEST_USERNAME)))
                .andExpect(jsonPath("$.originalPrice", is(expectedOriginalPrice), BigDecimal.class))
                .andExpect(jsonPath("$.discount", comparesEqualTo(expectedDiscount), BigDecimal.class))
                .andExpect(jsonPath("$.price", comparesEqualTo(expectedPrice), BigDecimal.class));
    }
}
