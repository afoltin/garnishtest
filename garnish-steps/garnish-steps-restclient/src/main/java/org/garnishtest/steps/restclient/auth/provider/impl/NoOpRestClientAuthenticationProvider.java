package org.garnishtest.steps.restclient.auth.provider.impl;

import org.garnishtest.modules.generic.httpclient.HttpRequestBuilder;
import org.garnishtest.steps.restclient.auth.provider.RestClientAuthenticationProvider;
import lombok.NonNull;

public final class NoOpRestClientAuthenticationProvider implements RestClientAuthenticationProvider {

    public void authenticateRequest(@NonNull final String username,
                                    @NonNull final HttpRequestBuilder requestBuilder) {
        // this implementation doesn't do anything
    }

}
