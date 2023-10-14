package com.bse.backend.assignment.coffeestore.security.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class JwtAuthenticationResponse {
    private String username;
    private String token;
}
