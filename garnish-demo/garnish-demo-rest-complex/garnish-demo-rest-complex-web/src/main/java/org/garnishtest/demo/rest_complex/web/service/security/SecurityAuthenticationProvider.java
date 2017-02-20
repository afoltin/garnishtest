package org.garnishtest.demo.rest_complex.web.service.security;

import org.garnishtest.demo.rest_complex.web.dao.mappers.UsersMapper;
import org.garnishtest.demo.rest_complex.web.dao.model.User;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public final class SecurityAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityAuthenticationProvider.class);

    @NonNull private final UsersMapper usersMapper;

    public SecurityAuthenticationProvider(@NonNull final UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return SecurityAuthentication.class.isAssignableFrom(authentication);
    }

    @Override
    public Authentication authenticate(@NonNull final Authentication authentication) throws AuthenticationException {
        final SecurityAuthentication securityAuthentication = (SecurityAuthentication) authentication;

        return authenticateCustomAuthenticationRequest(securityAuthentication);
    }

    @NonNull
    private Authentication authenticateCustomAuthenticationRequest(@NonNull final SecurityAuthentication authentication) {
        LOGGER.debug("retrieving user by token");

        final String token = authentication.getToken();
        final User user = this.usersMapper.getUserWithAddressByAuthToken(token);
        if (user == null) {
            LOGGER.debug("cannot find a user for the token [{}]", token);
            throw new BadCredentialsException("cannot find a user for the given token");
        }

        final SecurityUser securityUser = SecurityUser.fromDbUser(user);
        authentication.setSecurityUser(securityUser);
        authentication.setAuthenticated(true);

        return authentication;
    }

}
