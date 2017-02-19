package org.garnishtest.steps.restclient.auth.provider.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.net.HttpHeaders;
import org.garnishtest.modules.generic.httpclient.HttpRequestBuilder;
import org.garnishtest.steps.restclient.auth.provider.RestClientAuthenticationProvider;
import lombok.NonNull;

import java.util.Map;

public final class TokenMappingRestClientAuthenticationProvider implements RestClientAuthenticationProvider {

    @NonNull private final String headerName;
    @NonNull private final ImmutableMap<String /*username*/, String /*headerValue*/> usernameToHeaderValuesMap;

    public TokenMappingRestClientAuthenticationProvider(@NonNull final Map<String, String> usernameToHeaderValuesMap) {
        this(
                HttpHeaders.AUTHORIZATION,
                usernameToHeaderValuesMap
        );
    }

    public TokenMappingRestClientAuthenticationProvider(@NonNull final String headerName,
                                                        @NonNull final Map<String, String> usernameToHeaderValuesMap) {
        this.headerName = headerName;
        this.usernameToHeaderValuesMap = ImmutableMap.copyOf(usernameToHeaderValuesMap);
    }

    @Override
    public void authenticateRequest(@NonNull final String username,
                                    @NonNull final HttpRequestBuilder requestBuilder) {
        final String authenticationToken = this.usernameToHeaderValuesMap.get(username);
        if (authenticationToken == null) {
            throw new IllegalArgumentException("cannot authenticate: cannot find header value for username [" + username + "]");
        }

        requestBuilder.addHeader(headerName, authenticationToken);
    }

}
