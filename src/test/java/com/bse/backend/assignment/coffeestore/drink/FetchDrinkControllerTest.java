package com.bse.backend.assignment.coffeestore.drink;

import com.bse.backend.assignment.coffeestore.drink.api.model.Drink;
import com.bse.backend.assignment.coffeestore.mock.WithMockCustomUser;
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
class FetchDrinkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Get all Drinks should return a list of Drinks with expected details")
    void getAllDrinksTest() throws Exception {
        // given
        List<Drink> expectedDrinks = List.of(
                new Drink(1L, "Black Coffee", BigDecimal.valueOf(4.00)),
                new Drink(2L, "Latte", BigDecimal.valueOf(5.00)),
                new Drink(3L, "Mocha", BigDecimal.valueOf(6.00)),
                new Drink(4L, "Tea", BigDecimal.valueOf(3.00))
        );

        // when
        var result = mockMvc.perform(get("/api/v1/drinks")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(expectedDrinks.size())));
        for (int i = 0; i < expectedDrinks.size(); i++) {
            Drink expected = expectedDrinks.get(i);
            result.andExpect(jsonPath("$[" + i + "].id").value(expected.getId()))
                    .andExpect(jsonPath("$[" + i + "].name").value(expected.getName()))
                    .andExpect(jsonPath("$[" + i + "].price").value(expected.getPrice()));
        }
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Get all Drinks (Anonymous) should return 403 Forbidden")
    void getAllDrinksAnonymousTest() throws Exception {
        // when
        var result = mockMvc.perform(get("/api/v1/drinks")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Get Drink by ID should return the expected Drink")
    void getDrinkByIdTest() throws Exception {
        // given
        var expectedDrink = new Drink(1L, "Black Coffee", BigDecimal.valueOf(4.00));

        // when
        var result = mockMvc.perform(get("/api/v1/drinks/" + expectedDrink.getId())
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedDrink.getId()))
                .andExpect(jsonPath("$.name").value(expectedDrink.getName()))
                .andExpect(jsonPath("$.price").value(expectedDrink.getPrice()));
    }

    @Test
    @DisplayName("Get Drink by Non-Existent ID should return 404 Not Found")
    void getDrinkByIdNonExistentTest() throws Exception {
        // given
        long id = 5L;

        // when
        var result = mockMvc.perform(get("/api/v1/drinks/" + id)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.reason", is(HttpStatus.NOT_FOUND.getReasonPhrase())))
                .andExpect(jsonPath("$.message", is("Drink not found")));
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Get Drink by ID (Anonymous) should return 403 Forbidden")
    void getDrinkByIdAnonymousTest() throws Exception {
        // when
        var result = mockMvc.perform(get("/api/v1/drinks/1")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isForbidden());
    }

}
