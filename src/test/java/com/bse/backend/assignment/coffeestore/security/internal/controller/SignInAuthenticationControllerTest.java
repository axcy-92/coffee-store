package com.bse.backend.assignment.coffeestore.security.internal.controller;

import com.bse.backend.assignment.coffeestore.security.api.model.Role;
import com.bse.backend.assignment.coffeestore.security.api.model.SignInRequest;
import com.bse.backend.assignment.coffeestore.security.internal.persistence.User;
import com.bse.backend.assignment.coffeestore.security.internal.persistence.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
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
class SignInAuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void signInUser_ShouldReturnJwtToken() throws Exception {
        // given
        String password = "qwerty";
        User user = prepareUser("anakyn.skywalker@example.com", password);
        var signInRequest = SignInRequest.builder()
                .email(user.getEmail())
                .password(password)
                .build();

        // when
        var result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInRequest)));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(signInRequest.getEmail())))
                .andExpect(jsonPath("$.token", notNullValue()));
    }

    @Test
    void signInUser_InvalidRequest_ShouldReturn400() throws Exception {
        // given
        var signInRequest = SignInRequest.builder()
                .email("Obi-Wan")
                .build();

        // when
        var result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auth/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signInRequest)));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.reason", is(HttpStatus.BAD_REQUEST.getReasonPhrase())))
                .andExpect(hasError("password", "must not be blank"))
                .andExpect(hasError("email", "must be a well-formed email address"));
    }

    @Test
    void signInUser_InvalidCredentials_ShouldReturn401() throws Exception {
        // given
        User user = prepareUser("kenobi@example.com", "qwerty");
        var signInRequest = SignInRequest.builder()
                .email(user.getEmail())
                .password("wrong_password")
                .build();

        // when
        var result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auth/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signInRequest)));

        // then
        result.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.reason", is(HttpStatus.UNAUTHORIZED.getReasonPhrase())))
                .andExpect(jsonPath("$.message", is("Bad credentials")));
    }

    private User prepareUser(String username, String password) {
        var user = User.builder()
                .email(username)
                .password(passwordEncoder.encode(password))
                .role(Role.USER)
                .build();
        return userRepository.save(user);
    }

    private ResultMatcher hasError(String fieldName, String errorMessage) {
        return jsonPath("$.errors", hasItems(
                hasEntry("fieldName", fieldName),
                hasEntry("errorMessage", errorMessage)
        ));
    }

}
