package com.mobiquityinc.mobit.steps.restclient.auth.provider.impl;

import com.mobiquityinc.mobit.modules.generic.httpclient.HttpRequestBuilder;
import com.mobiquityinc.mobit.steps.restclient.auth.provider.RestClientAuthenticationProvider;
import lombok.NonNull;

public final class NoOpRestClientAuthenticationProvider implements RestClientAuthenticationProvider {

    public void authenticateRequest(@NonNull final String username,
                                    @NonNull final HttpRequestBuilder requestBuilder) {
        // this implementation doesn't do anything
    }

}
