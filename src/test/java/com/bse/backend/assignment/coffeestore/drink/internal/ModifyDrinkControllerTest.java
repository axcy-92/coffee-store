package com.bse.backend.assignment.coffeestore.drink.internal;

import com.bse.backend.assignment.coffeestore.drink.api.model.InputDrink;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
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

    @Test
    @WithMockUser(roles = "ADMIN")
    void createNewDrink_ShouldReturnNewDrink() throws Exception {
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
    void createNewDrink_InvalidRequest_ShouldReturn400() throws Exception {
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
                .andExpect(hasError("name", "must not be blank"))
                .andExpect(hasError("price", "must be greater than 0"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void createNewDrink_User_ShouldReturn403() throws Exception {
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
    void createNewDrink_Anonymous_ShouldReturn403() throws Exception {
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
    void updateDrink_ShouldReturnUpdatedDrink() throws Exception {
        // given
        long drinkId = 1L;
        var updatedDrink = InputDrink.builder()
                .name("Updated Drink")
                .price(BigDecimal.ONE)
                .build();

        // when
        var result = mockMvc.perform(put("/api/v1/drinks/" + drinkId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDrink)));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(drinkId), Long.class))
                .andExpect(jsonPath("$.name", is(updatedDrink.getName())))
                .andExpect(jsonPath("$.price", is(updatedDrink.getPrice()), BigDecimal.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateDrink_NotExist_ShouldReturn404() throws Exception {
        // given
        long drinkId = -1L;
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
    void updateDrink_User_ShouldReturn403() throws Exception {
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
    void updateDrink_Anonymous_ShouldReturn403() throws Exception {
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
    void deleteDrink_ShouldDelete() throws Exception {
        // given
        long drinkId = 1L;

        // when
        var result = mockMvc.perform(delete("/api/v1/drinks/" + drinkId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "USER")
    void deleteDrink_User_ShouldReturn403() throws Exception {
        // given
        long drinkId = 1L;

        // when
        var result = mockMvc.perform(delete("/api/v1/drinks/" + drinkId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isForbidden());
    }

    @Test
    void deleteDrink_Anonymous_ShouldReturn403() throws Exception {
        // given
        long drinkId = 1L;

        // when
        var result = mockMvc.perform(delete("/api/v1/drinks/" + drinkId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isForbidden());
    }

    private ResultMatcher hasError(String fieldName, String errorMessage) {
        return jsonPath("$.errors", hasItems(
                hasEntry("fieldName", fieldName),
                hasEntry("errorMessage", errorMessage)
        ));
    }
}
