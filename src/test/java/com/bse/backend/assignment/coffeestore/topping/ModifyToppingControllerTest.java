package com.bse.backend.assignment.coffeestore.topping;

import com.bse.backend.assignment.coffeestore.mock.WithMockCustomUser;
import com.bse.backend.assignment.coffeestore.security.api.model.Role;
import com.bse.backend.assignment.coffeestore.topping.api.model.InputTopping;
import com.bse.backend.assignment.coffeestore.topping.internal.persistence.ToppingEntity;
import com.bse.backend.assignment.coffeestore.topping.internal.persistence.ToppingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@WithMockCustomUser(role = Role.ADMIN)
class ModifyToppingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ToppingRepository repository;

    @Test
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
                .andExpect(jsonPath("$.errors..fieldName", hasItems("name", "price")))
                .andExpect(jsonPath("$.errors..errorMessage", hasItems("must not be blank", "must be greater than 0")));
    }

    @Test
    @WithMockCustomUser(role = Role.USER)
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
    @WithAnonymousUser
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
    void updateTopping_ShouldReturnUpdatedTopping() throws Exception {
        // given
        var inputTopping = InputTopping.builder()
                .name("Updated Topping")
                .price(BigDecimal.ONE)
                .build();
        var entity = ToppingEntity.builder()
                .name("Topping for update")
                .price(BigDecimal.ZERO)
                .build();
        entity = repository.save(entity);

        // when
        var result = mockMvc.perform(put("/api/v1/toppings/" + entity.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputTopping)));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(entity.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(inputTopping.getName())))
                .andExpect(jsonPath("$.price", is(inputTopping.getPrice()), BigDecimal.class));
    }

    @Test
    void updateTopping_NotExist_ShouldReturn404() throws Exception {
        // given
        long toppingId = 5L;
        var inputTopping = InputTopping.builder()
                .name("Updated Not Exist Topping")
                .price(BigDecimal.ONE)
                .build();

        // when
        var result = mockMvc.perform(put("/api/v1/toppings/" + toppingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputTopping)));

        // then
        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.reason", is(HttpStatus.NOT_FOUND.getReasonPhrase())))
                .andExpect(jsonPath("$.message", is("Topping not found")));
    }

    @Test
    @WithMockCustomUser(role = Role.USER)
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
    @WithAnonymousUser
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
    void deleteTopping_ShouldDelete() throws Exception {
        // given
        var entity = ToppingEntity.builder()
                .name("Topping for delete")
                .price(BigDecimal.ZERO)
                .build();
        entity = repository.save(entity);

        // when
        var result = mockMvc.perform(delete("/api/v1/toppings/" + entity.getId())
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNoContent());
        assertTrue(repository.findById(entity.getId()).isEmpty());
    }

    @Test
    @WithMockCustomUser(role = Role.USER)
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
    @WithAnonymousUser
    void deleteTopping_Anonymous_ShouldReturn403() throws Exception {
        // given
        long toppingId = 1L;

        // when
        var result = mockMvc.perform(delete("/api/v1/toppings/" + toppingId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isForbidden());
    }
}
