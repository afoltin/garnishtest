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
            throw new RuntimeException("incorrect username or password"); // todo: proper JSON errors from controllers
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
