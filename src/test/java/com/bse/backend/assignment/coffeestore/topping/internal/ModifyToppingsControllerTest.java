package com.bse.backend.assignment.coffeestore.topping.internal;

import com.bse.backend.assignment.coffeestore.topping.api.model.InputTopping;
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
class ModifyToppingsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void createNewTopping_ShouldReturnNewTopping() throws Exception {
        // given
        var inputTopping = InputTopping.builder()
                .name("New Topping")
                .price(BigDecimal.TEN)
                .build();

        // when
        var result = mockMvc.perform(post("/api/v1/toppings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputTopping)));

        // then
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is(inputTopping.getName())))
                .andExpect(jsonPath("$.price", is(inputTopping.getPrice()), BigDecimal.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createNewTopping_InvalidRequest_ShouldReturn400() throws Exception {
        // given
        var inputTopping = InputTopping.builder()
                .name("")
                .price(BigDecimal.ZERO)
                .build();

        // when
        var result = mockMvc.perform(post("/api/v1/toppings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputTopping)));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.reason", is(HttpStatus.BAD_REQUEST.getReasonPhrase())))
                .andExpect(hasError("name", "must not be blank"))
                .andExpect(hasError("price", "must be greater than 0"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void createNewTopping_User_ShouldReturn403() throws Exception {
        // given
        var inputTopping = InputTopping.builder()
                .name("User Topping")
                .price(BigDecimal.TEN)
                .build();

        // when
        var result = mockMvc.perform(post("/api/v1/toppings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputTopping)));

        // then
        result.andExpect(status().isForbidden());
    }

    @Test
    void createNewTopping_Anonymous_ShouldReturn403() throws Exception {
        // given
        var inputTopping = InputTopping.builder()
                .name("Anonymous Topping")
                .price(BigDecimal.TEN)
                .build();

        // when
        var result = mockMvc.perform(post("/api/v1/toppings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputTopping)));

        // then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateTopping_ShouldReturnUpdatedTopping() throws Exception {
        // given
        long toppingId = 1L;
        var updatedTopping = InputTopping.builder()
                .name("Updated Topping")
                .price(BigDecimal.ONE)
                .build();

        // when
        var result = mockMvc.perform(put("/api/v1/toppings/" + toppingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedTopping)));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(toppingId), Long.class))
                .andExpect(jsonPath("$.name", is(updatedTopping.getName())))
                .andExpect(jsonPath("$.price", is(updatedTopping.getPrice()), BigDecimal.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateTopping_NotExist_ShouldReturn404() throws Exception {
        // given
        long toppingId = -1L;
        var updatedTopping = InputTopping.builder()
                .name("Updated Not Exist Topping")
                .price(BigDecimal.ONE)
                .build();

        // when
        var result = mockMvc.perform(put("/api/v1/toppings/" + toppingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedTopping)));

        // then
        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.reason", is(HttpStatus.NOT_FOUND.getReasonPhrase())))
                .andExpect(jsonPath("$.message", is("Topping not found")));
    }

    @Test
    @WithMockUser(roles = "USER")
    void updateTopping_User_ShouldReturn403() throws Exception {
        // given
        long toppingId = 1L;
        var updatedTopping = InputTopping.builder()
                .name("User Topping")
                .price(BigDecimal.TEN)
                .build();

        // when
        var result = mockMvc.perform(put("/api/v1/toppings/" + toppingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedTopping)));

        // then
        result.andExpect(status().isForbidden());
    }

    @Test
    void updateTopping_Anonymous_ShouldReturn403() throws Exception {
        // given
        long toppingId = 1L;
        var updatedTopping = InputTopping.builder()
                .name("Anonymous Topping")
                .price(BigDecimal.TEN)
                .build();

        // when
        var result = mockMvc.perform(put("/api/v1/toppings/" + toppingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedTopping)));

        // then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteTopping_ShouldDelete() throws Exception {
        // given
        long toppingId = 1L;

        // when
        var result = mockMvc.perform(delete("/api/v1/toppings/" + toppingId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "USER")
    void deleteTopping_User_ShouldReturn403() throws Exception {
        // given
        long toppingId = 1L;

        // when
        var result = mockMvc.perform(delete("/api/v1/toppings/" + toppingId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isForbidden());
    }

    @Test
    void deleteTopping_Anonymous_ShouldReturn403() throws Exception {
        // given
        long toppingId = 1L;

        // when
        var result = mockMvc.perform(delete("/api/v1/toppings/" + toppingId)
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
