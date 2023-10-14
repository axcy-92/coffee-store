package com.bse.backend.assignment.coffeestore.security.internal.controller;

import com.bse.backend.assignment.coffeestore.security.api.model.SignUpRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SignUpAuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void signUpUser_ShouldReturnJwtToken() throws Exception {
        // given
        var signUpRequest = SignUpRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("asd")
                .build();

        // when
        var result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(signUpRequest.getEmail())))
                .andExpect(jsonPath("$.token", notNullValue()));
    }

    @Test
    void signUpUser_InvalidRequest_ShouldReturn400() throws Exception {
        // given
        var invalidSignUpRequest = SignUpRequest.builder()
                .firstName("Jack")
                .lastName("Sparrow")
                .email("jack.sparrow")
                .build();

        // when
        var result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidSignUpRequest)));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.reason", is(HttpStatus.BAD_REQUEST.getReasonPhrase())))
                .andExpect(hasError("password", "must not be blank"))
                .andExpect(hasError("email", "must be a well-formed email address"));
    }

    @Test
    void signUpUser_AlreadyRegistered_ShouldReturn409() throws Exception {
        // given
        var signUpRequest = SignUpRequest.builder()
                .firstName("John")
                .lastName("Smith")
                .email("john.smith@example.com")
                .password("qwerty")
                .build();

        // when
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)));
        var result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)));

        // then
        result.andExpect(status().isConflict())
                .andExpect(jsonPath("$.reason", is(HttpStatus.CONFLICT.getReasonPhrase())))
                .andExpect(jsonPath("$.message", is("A user with this email already exists")));
    }

    private ResultMatcher hasError(String fieldName, String errorMessage) {
        return jsonPath("$.errors", hasItems(
                hasEntry("fieldName", fieldName),
                hasEntry("errorMessage", errorMessage)
        ));
    }
}
