package com.bse.backend.assignment.coffeestore.security;

import com.bse.backend.assignment.coffeestore.security.api.model.Role;
import com.bse.backend.assignment.coffeestore.security.api.model.SignUpRequest;
import com.bse.backend.assignment.coffeestore.security.internal.persistence.UserEntity;
import com.bse.backend.assignment.coffeestore.security.internal.persistence.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Sign-Up User should return JWT Token")
    void signUpUserTest() throws Exception {
        // given
        var signUpRequest = SignUpRequest.builder()
                .firstName("Jack")
                .lastName("Sparrow")
                .email("jack.sparrow@email.com")
                .password("qwerty")
                .build();

        // when
        var result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.user.email", is(signUpRequest.getEmail())))
                .andExpect(jsonPath("$.user.firstName", is(signUpRequest.getFirstName())))
                .andExpect(jsonPath("$.user.lastName", is(signUpRequest.getLastName())))
                .andExpect(jsonPath("$.token", notNullValue()));
    }

    @Test
    @DisplayName("Sign-Up User with invalid data should return 400 Bad Request")
    void signUpUserInvalidDataTest() throws Exception {
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
                .andExpect(jsonPath("$.errors..fieldName", hasItems("password", "email")))
                .andExpect(jsonPath("$.errors..errorMessage",
                        hasItems("must not be blank", "must be a well-formed email address")));
    }

    @Test
    @DisplayName("Sign-Up User already registered should return 409 Conflict")
    void signUpUserAlreadyRegisteredTest() throws Exception {
        // given
        UserEntity userEntity = userRepository.save(UserEntity.builder()
                .email("jack.sparrow@email.com")
                .firstName("Jack")
                .lastName("Sparrow")
                .password("qwerty")
                .role(Role.USER)
                .build());
        var signUpRequest = SignUpRequest.builder()
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .build();

        // when
        var result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)));

        // then
        result.andExpect(status().isConflict())
                .andExpect(jsonPath("$.reason", is(HttpStatus.CONFLICT.getReasonPhrase())))
                .andExpect(jsonPath("$.message", is("A user with this email already exists")));
    }
}
