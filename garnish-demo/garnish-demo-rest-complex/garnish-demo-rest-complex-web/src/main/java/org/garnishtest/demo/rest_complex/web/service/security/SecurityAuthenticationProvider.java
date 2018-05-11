/*
 * Copyright 2016-2018, Garnish.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

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
