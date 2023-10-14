package com.bse.backend.assignment.coffeestore.security.internal.config;

import com.bse.backend.assignment.coffeestore.security.api.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String HEADER_PREFIX = "Bearer ";
    private static final String REQUEST_HEADER = "Authorization";

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        final String authenticationHeader = request.getHeader(REQUEST_HEADER);

        if (!validateHeader(authenticationHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = extractJwtToken(authenticationHeader);
            final String username = jwtService.extractUserName(jwt);

            UserDetails userDetails = isUserFound(username)
                    ? userDetailsService.loadUserByUsername(username)
                    : null;

            if (userDetails != null && jwtService.isTokenValid(jwt, userDetails)) {
                var usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException expiredJwtException) {
            handlerExceptionResolver.resolveException(request, response, null, expiredJwtException);
        }
    }

    private boolean isUserFound(String username) {
        return username != null && SecurityContextHolder.getContext().getAuthentication() == null;
    }

    private String extractJwtToken(String authenticationHeader) {
        return authenticationHeader.substring(7);
    }

    private boolean validateHeader(String authenticationHeader) {
        return authenticationHeader != null && authenticationHeader.startsWith(HEADER_PREFIX);
    }
}