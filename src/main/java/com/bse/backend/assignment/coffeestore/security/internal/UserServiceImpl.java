package com.bse.backend.assignment.coffeestore.security.internal;

import com.bse.backend.assignment.coffeestore.security.api.UserService;
import com.bse.backend.assignment.coffeestore.security.api.JwtService;
import com.bse.backend.assignment.coffeestore.security.api.exception.UserAlreadyExistsException;
import com.bse.backend.assignment.coffeestore.security.api.model.AuthenticationResponse;
import com.bse.backend.assignment.coffeestore.security.api.model.SignInRequest;
import com.bse.backend.assignment.coffeestore.security.api.model.SignUpRequest;
import com.bse.backend.assignment.coffeestore.security.api.model.UserResponse;
import com.bse.backend.assignment.coffeestore.security.internal.persistence.UserEntity;
import com.bse.backend.assignment.coffeestore.security.internal.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper mapper;

    @Override
    public AuthenticationResponse create(SignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail()))
            throw new UserAlreadyExistsException("A user with this email already exists");

        var user = userRepository.save(UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build());
        String jwt = jwtService.generateToken(user);
        log.debug("New User has been successfully created: {}", user);

        return mapper.toDto(user, jwt);
    }

    @Override
    public AuthenticationResponse login(SignInRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserEntity user = (UserEntity) authentication.getPrincipal();
        String jwt = jwtService.generateToken(user);
        log.debug("Successful login as user {}", user);

        return mapper.toDto(user, jwt);
    }

    @Override
    public Optional<UserResponse> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) return Optional.empty();

        var principal = authentication.getPrincipal();
        if (principal instanceof UserEntity userEntity) {
            log.debug("Retrieved current user: {}", userEntity);
            return Optional.ofNullable(mapper.toDto(userEntity));
        } else {
            log.error("Failed to retrieve current user. Principal: {} not instance of UserEntity", principal);
            return Optional.empty();
        }
    }
}
