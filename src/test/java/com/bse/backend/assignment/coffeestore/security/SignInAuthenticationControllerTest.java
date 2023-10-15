package com.bse.backend.assignment.coffeestore.security;

import com.bse.backend.assignment.coffeestore.security.api.model.Role;
import com.bse.backend.assignment.coffeestore.security.api.model.SignInRequest;
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
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @DisplayName("Sign-In User should return JWT Token")
    void signInUserTest() throws Exception {
        // given
        String password = "qwerty";
        UserEntity userEntity = userRepository.save(UserEntity.builder()
                .email("jack.sparrow@email.com")
                .firstName("Jack")
                .lastName("Sparrow")
                .password(passwordEncoder.encode(password))
                .role(Role.USER)
                .build());
        var signInRequest = SignInRequest.builder()
                .email(userEntity.getEmail())
                .password(password)
                .build();

        // when
        var result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInRequest)));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.user.email", is(signInRequest.getEmail())))
                .andExpect(jsonPath("$.user.firstName", is(userEntity.getFirstName())))
                .andExpect(jsonPath("$.user.lastName", is(userEntity.getLastName())))
                .andExpect(jsonPath("$.token", notNullValue()));
    }

    @Test
    @DisplayName("Sign-In User with invalid data should return 400 Bad Request")
    void signInUserInvalidDataTest() throws Exception {
        // given
        var signInRequest = SignInRequest.builder()
                .email("jack.sparrow")
                .build();

        // when
        var result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auth/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signInRequest)));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.reason", is(HttpStatus.BAD_REQUEST.getReasonPhrase())))
                .andExpect(jsonPath("$.errors..fieldName", hasItems("password", "email")))
                .andExpect(jsonPath("$.errors..errorMessage",
                        hasItems("must not be blank", "must be a well-formed email address")));
    }

    @Test
    @DisplayName("Sign-In User with invalid credentials should return 401 Unauthorized")
    void signInUserInvalidCredentialsTest() throws Exception {
        // given
        UserEntity userEntity = userRepository.save(UserEntity.builder()
                .email("jack.sparrow@email.com")
                .password(passwordEncoder.encode("qwerty"))
                .role(Role.USER)
                .build());
        var signInRequest = SignInRequest.builder()
                .email(userEntity.getEmail())
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
}
