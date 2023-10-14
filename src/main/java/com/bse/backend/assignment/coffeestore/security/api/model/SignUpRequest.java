package com.bse.backend.assignment.coffeestore.security.api.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class SignUpRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -6301141775239465882L;

    private String firstName;
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull
    @NotBlank
    private String password;

    private Role role = Role.USER;
}

