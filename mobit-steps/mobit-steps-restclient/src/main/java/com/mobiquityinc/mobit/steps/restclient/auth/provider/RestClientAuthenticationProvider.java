package com.mobiquityinc.mobit.steps.restclient.auth.provider;

import com.mobiquityinc.mobit.modules.generic.httpclient.HttpRequestBuilder;
import lombok.NonNull;

public interface RestClientAuthenticationProvider {

    void authenticateRequest(@NonNull String username,
                             @NonNull HttpRequestBuilder requestBuilder);

}
