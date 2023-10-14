package com.bse.backend.assignment.coffeestore.security.internal;

import com.bse.backend.assignment.coffeestore.security.api.AuthenticationService;
import com.bse.backend.assignment.coffeestore.security.api.JwtService;
import com.bse.backend.assignment.coffeestore.security.api.exception.UserAlreadyExistsException;
import com.bse.backend.assignment.coffeestore.security.api.model.JwtAuthenticationResponse;
import com.bse.backend.assignment.coffeestore.security.api.model.SignInRequest;
import com.bse.backend.assignment.coffeestore.security.api.model.SignUpRequest;
import com.bse.backend.assignment.coffeestore.security.internal.persistence.User;
import com.bse.backend.assignment.coffeestore.security.internal.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        // Check if a user with the specified email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("A user with this email already exists");
        }

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);
        String jwt = jwtService.generateToken(user);

        return JwtAuthenticationResponse.builder()
                .username(user.getUsername())
                .token(jwt)
                .build();
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = (User) authentication.getPrincipal();
        String jwt = jwtService.generateToken(user);

        return JwtAuthenticationResponse.builder()
                .username(user.getUsername())
                .token(jwt)
                .build();
    }
}
