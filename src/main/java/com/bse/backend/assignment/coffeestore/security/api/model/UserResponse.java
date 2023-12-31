package com.bse.backend.assignment.coffeestore.security.api.model;

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
public class UserResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -281875756582383244L;

    private String email;
    private String firstName;
    private String lastName;
}