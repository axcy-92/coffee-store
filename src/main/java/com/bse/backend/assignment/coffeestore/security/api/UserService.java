package com.bse.backend.assignment.coffeestore.security.api;

import com.bse.backend.assignment.coffeestore.security.api.model.AuthenticationResponse;
import com.bse.backend.assignment.coffeestore.security.api.model.SignInRequest;
import com.bse.backend.assignment.coffeestore.security.api.model.SignUpRequest;
import com.bse.backend.assignment.coffeestore.security.api.model.UserResponse;

import java.util.Optional;

public interface UserService {
    AuthenticationResponse create(SignUpRequest request);
    AuthenticationResponse login(SignInRequest request);
    Optional<UserResponse> getCurrentUser();
}
