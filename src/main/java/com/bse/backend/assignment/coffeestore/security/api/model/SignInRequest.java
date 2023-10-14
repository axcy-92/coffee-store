package com.bse.backend.assignment.coffeestore.security.api.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class SignInRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -7449302007171973210L;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
