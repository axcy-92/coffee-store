package com.bse.backend.assignment.coffeestore.mock;

import com.bse.backend.assignment.coffeestore.security.internal.persistence.UserEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UserDetails userEntity = UserEntity.builder()
                .email(annotation.username())
                .role(annotation.role())
                .build();

        Authentication auth =
                new UsernamePasswordAuthenticationToken(userEntity, "password", userEntity.getAuthorities());
        context.setAuthentication(auth);

        return context;
    }
}
