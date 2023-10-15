package com.bse.backend.assignment.coffeestore.drink;

import com.bse.backend.assignment.coffeestore.drink.api.model.InputDrink;
import com.bse.backend.assignment.coffeestore.drink.internal.persistence.DrinkEntity;
import com.bse.backend.assignment.coffeestore.drink.internal.persistence.DrinkRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ModifyDrinkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DrinkRepository repository;

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Create new Drink should return new Drink")
    void createNewDrinkTest() throws Exception {
        // given
        var inputDrink = InputDrink.builder()
                .name("New Drink")
                .price(BigDecimal.TEN)
                .build();

        // when
        var result = mockMvc.perform(post("/api/v1/drinks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDrink)));

        // then
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is(inputDrink.getName())))
                .andExpect(jsonPath("$.price", is(inputDrink.getPrice()), BigDecimal.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Create new Drink with invalid data should return 400 Bad Request")
    void createNewDrinkInvalidDataTest() throws Exception {
        // given
        var inputDrink = InputDrink.builder()
                .name("")
                .price(BigDecimal.ZERO)
                .build();

        // when
        var result = mockMvc.perform(post("/api/v1/drinks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDrink)));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.reason", is(HttpStatus.BAD_REQUEST.getReasonPhrase())))
                .andExpect(jsonPath("$.errors..fieldName", hasItems("name", "price")))
                .andExpect(jsonPath("$.errors..errorMessage", hasItems("must not be blank", "must be greater than 0")));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Create new Drink (USER) should return 403 Forbidden")
    void createNewDrinkUserTest() throws Exception {
        // given
        var inputDrink = InputDrink.builder()
                .name("User Drink")
                .price(BigDecimal.TEN)
                .build();

        // when
        var result = mockMvc.perform(post("/api/v1/drinks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDrink)));

        // then
        result.andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Create new Drink (Anonymous) should return 403 Forbidden")
    void createNewDrinkAnonymousTest() throws Exception {
        // given
        var inputDrink = InputDrink.builder()
                .name("Anonymous Drink")
                .price(BigDecimal.TEN)
                .build();

        // when
        var result = mockMvc.perform(post("/api/v1/drinks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDrink)));

        // then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Update Drink should return updated Drink")
    void updateDrinkTest() throws Exception {
        // given
        var updatedDrink = InputDrink.builder()
                .name("Updated Drink")
                .price(BigDecimal.ONE)
                .build();
        var entity = DrinkEntity.builder()
                .name("Drink for update")
                .price(BigDecimal.ZERO)
                .build();
        entity = repository.save(entity);

        // when
        var result = mockMvc.perform(put("/api/v1/drinks/" + entity.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDrink)));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(entity.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(updatedDrink.getName())))
                .andExpect(jsonPath("$.price", is(updatedDrink.getPrice()), BigDecimal.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Update Drink with Non-Existent ID should return 404 Not Found")
    void updateDrinkNonExistentTest() throws Exception {
        // given
        long drinkId = 5L;
        var updatedDrink = InputDrink.builder()
                .name("Updated Not Exist Drink")
                .price(BigDecimal.ONE)
                .build();

        // when
        var result = mockMvc.perform(put("/api/v1/drinks/" + drinkId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDrink)));

        // then
        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.reason", is(HttpStatus.NOT_FOUND.getReasonPhrase())))
                .andExpect(jsonPath("$.message", is("Drink not found")));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Update Drink (USER) should return 403 Forbidden")
    void updateDrinkUserTest() throws Exception {
        // given
        long drinkId = 1L;
        var updatedDrink = InputDrink.builder()
                .name("User Drink")
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
    @DisplayName("Update Drink (Anonymous) should return 403 Forbidden")
    void updateDrinkAnonymousTest() throws Exception {
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
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Delete Drink should remove the Drink")
    void deleteDrinkTest() throws Exception {
        // given
        var entity = DrinkEntity.builder()
                .name("Topping for delete")
                .price(BigDecimal.ZERO)
                .build();
        entity = repository.save(entity);

        // when
        var result = mockMvc.perform(delete("/api/v1/drinks/" + entity.getId())
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNoContent());
        assertTrue(repository.findById(entity.getId()).isEmpty());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Delete Drink (USER) should return 403 Forbidden")
    void deleteDrinkUserTest() throws Exception {
        // given
        long drinkId = 1L;

        // when
        var result = mockMvc.perform(delete("/api/v1/drinks/" + drinkId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Delete Drink (Anonymous) should return 403 Forbidden")
    void deleteDrinkAnonymousTest() throws Exception {
        // given
        long drinkId = 1L;

        // when
        var result = mockMvc.perform(delete("/api/v1/drinks/" + drinkId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isForbidden());
    }
}
