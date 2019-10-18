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

package org.garnishtest.demo.rest_complex.web.service;

import org.garnishtest.demo.rest_complex.web.dao.mappers.AuthTokensMapper;
import org.garnishtest.demo.rest_complex.web.dao.model.AuthToken;
import lombok.NonNull;

import java.util.Date;

public final class AuthTokensService {

    @NonNull private final UsersService usersService;
    @NonNull private final AuthTokensMapper authTokensMapper;
    @NonNull private final TokenGenerator tokenGenerator;

    public AuthTokensService(@NonNull final UsersService usersService,
                             @NonNull final AuthTokensMapper authTokensMapper,
                             @NonNull final TokenGenerator tokenGenerator) {
        this.usersService = usersService;
        this.authTokensMapper = authTokensMapper;
        this.tokenGenerator = tokenGenerator;
    }

    public AuthToken createToken(@NonNull final String username,
                                 @NonNull final String password) {
        final Long userId = this.usersService.getUserIdByUsernameAndPassword(username, password);

        if (userId == null) {
            throw new AuthTokensServiceException(
                "incorrect username or password"); // todo: proper JSON errors from controllers
        }

        final AuthToken authToken = new AuthToken();

        authToken.setToken(
                this.tokenGenerator.generateNewAuthenticationToken()
        );
        authToken.setDateCreated(
                new Date()
        );

        this.authTokensMapper.createTokenForUser(userId, authToken);

        return authToken;
    }
}
