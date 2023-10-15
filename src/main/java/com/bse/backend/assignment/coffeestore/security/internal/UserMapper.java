package com.bse.backend.assignment.coffeestore.security.internal;

import com.bse.backend.assignment.coffeestore.security.api.model.AuthenticationResponse;
import com.bse.backend.assignment.coffeestore.security.api.model.UserResponse;
import com.bse.backend.assignment.coffeestore.security.internal.persistence.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    AuthenticationResponse toDto(UserEntity user, String token);
    UserResponse toDto(UserEntity user);
}
