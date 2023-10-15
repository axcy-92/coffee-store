package com.bse.backend.assignment.coffeestore.mock;

import com.bse.backend.assignment.coffeestore.security.api.model.Role;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
    String username() default "testUser@email.com";

    Role role() default Role.USER;
}
