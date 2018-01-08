package org.garnishtest.demo.rest_complex.web.service.security;

import com.google.common.net.HttpHeaders;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Nonnull;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class TokenAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

    @Nonnull private final AuthenticationManager authenticationManager;
    @Nonnull private final AuthenticationEntryPoint authenticationEntryPoint;

    public TokenAuthenticationFilter(@Nonnull final AuthenticationManager authenticationManager,
                                     @Nonnull final AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationManager = authenticationManager;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void doFilterInternal(@NonNull final HttpServletRequest request,
                                    @NonNull final HttpServletResponse response,
                                    @NonNull final FilterChain chain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null) {
            LOGGER.debug("commencing entry point because the HTTP request does not contain an {} header", HttpHeaders.AUTHORIZATION);
            this.authenticationEntryPoint.commence(request, response, null);
            return;
        }

        try {
            LOGGER.debug(
                    "HTTP request contains an {} header"
                    + ", invoking authentication manager to check if the authentication token is valid",
                    HttpHeaders.AUTHORIZATION
            );

            final SecurityAuthentication authenticationRequest = new SecurityAuthentication(authorizationHeader);
            final Authentication authenticationResult = this.authenticationManager.authenticate(authenticationRequest);

            LOGGER.debug("HTTP request contains a valid authentication token; setting it into the security context");

            SecurityContextHolder.getContext().setAuthentication(authenticationResult);

            chain.doFilter(request, response);
        } catch (final AuthenticationException e) {
            SecurityContextHolder.clearContext();

            LOGGER.debug("Authentication request failed", e);

            this.authenticationEntryPoint.commence(request, response, e);
        }
    }

}
