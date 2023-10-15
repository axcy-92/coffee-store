package com.bse.backend.assignment.coffeestore.topping;

import com.bse.backend.assignment.coffeestore.mock.WithMockCustomUser;
import com.bse.backend.assignment.coffeestore.topping.api.model.Topping;
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
@WithMockCustomUser
class FetchToppingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Get all Toppings should return a list of Toppings with expected details")
    void getAllToppingsTest() throws Exception {
        // given
        List<Topping> expectedToppings = List.of(
                new Topping(1L, "Milk", BigDecimal.valueOf(2.00)),
                new Topping(2L, "Hazelnut syrup", BigDecimal.valueOf(3.00)),
                new Topping(3L, "Chocolate sauce", BigDecimal.valueOf(5.00)),
                new Topping(4L, "Lemon", BigDecimal.valueOf(2.00))
        );

        // when
        var result = mockMvc.perform(get("/api/v1/toppings")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(expectedToppings.size())));
        for (int i = 0; i < expectedToppings.size(); i++) {
            Topping expected = expectedToppings.get(i);
            result.andExpect(jsonPath("$[" + i + "].id").value(expected.getId()))
                    .andExpect(jsonPath("$[" + i + "].name").value(expected.getName()))
                    .andExpect(jsonPath("$[" + i + "].price").value(expected.getPrice()));
        }
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Get all Toppings (Anonymous) should return 403 Forbidden")
    void getAllToppingsAnonymousTest() throws Exception {
        // when
        var result = mockMvc.perform(get("/api/v1/toppings")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Get Topping by ID should return the expected Topping")
    void getToppingByIdTest() throws Exception {
        // given
        var expectedTopping = new Topping(1L, "Milk", BigDecimal.valueOf(2.00));

        // when
        var result = mockMvc.perform(get("/api/v1/toppings/" + expectedTopping.getId())
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedTopping.getId()))
                .andExpect(jsonPath("$.name").value(expectedTopping.getName()))
                .andExpect(jsonPath("$.price").value(expectedTopping.getPrice()));
    }

    @Test
    @DisplayName("Get Topping by Non-Existent ID should return 404 Not Found")
    void getToppingById_NotExistTopping_ShouldReturn404() throws Exception {
        // given
        long id = 5L;

        // when
        var result = mockMvc.perform(get("/api/v1/toppings/" + id)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.reason", is(HttpStatus.NOT_FOUND.getReasonPhrase())))
                .andExpect(jsonPath("$.message", is("Topping not found")));
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Get Topping by ID (Anonymous) should return 403 Forbidden")
    void getToppingByIdAnonymousTest() throws Exception {
        // when
        var result = mockMvc.perform(get("/api/v1/toppings/1")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isForbidden());
    }

}
