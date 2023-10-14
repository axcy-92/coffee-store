package com.bse.backend.assignment.coffeestore.security.internal.controller;

import com.bse.backend.assignment.coffeestore.security.api.AuthenticationController;
import com.bse.backend.assignment.coffeestore.security.api.AuthenticationService;
import com.bse.backend.assignment.coffeestore.security.api.model.JwtAuthenticationResponse;
import com.bse.backend.assignment.coffeestore.security.api.model.SignInRequest;
import com.bse.backend.assignment.coffeestore.security.api.model.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationControllerImpl implements AuthenticationController {

    private final AuthenticationService service;

    @Override
    @PostMapping("/sign-up")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody SignUpRequest request) {
        JwtAuthenticationResponse response = service.signUp(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInRequest request) {
        JwtAuthenticationResponse response = service.signIn(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
