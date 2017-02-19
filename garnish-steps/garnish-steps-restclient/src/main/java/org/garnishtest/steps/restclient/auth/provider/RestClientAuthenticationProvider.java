package org.garnishtest.steps.restclient.auth.provider;

import org.garnishtest.modules.generic.httpclient.HttpRequestBuilder;
import lombok.NonNull;

public interface RestClientAuthenticationProvider {

    void authenticateRequest(@NonNull String username,
                             @NonNull HttpRequestBuilder requestBuilder);

}
