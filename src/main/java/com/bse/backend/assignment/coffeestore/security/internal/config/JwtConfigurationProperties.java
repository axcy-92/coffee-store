package com.bse.backend.assignment.coffeestore.security.internal.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("coffee-store.token")
@Data
public class JwtConfigurationProperties {
    private String signingKey;

}
