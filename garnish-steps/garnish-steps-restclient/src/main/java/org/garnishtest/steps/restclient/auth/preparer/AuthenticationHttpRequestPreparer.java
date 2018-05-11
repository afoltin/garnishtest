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

package org.garnishtest.steps.restclient.auth.preparer;

import org.garnishtest.modules.generic.httpclient.HttpRequestBuilder;
import org.garnishtest.modules.generic.httpclient.request_preparer.HttpRequestPreparer;
import org.garnishtest.steps.restclient.auth.provider.RestClientAuthenticationProvider;
import lombok.NonNull;

import javax.annotation.Nullable;

public final class AuthenticationHttpRequestPreparer implements HttpRequestPreparer {

    @NonNull private final RestClientAuthenticationProvider authenticationProvider;

    @Nullable private String username;

    public AuthenticationHttpRequestPreparer(@NonNull final RestClientAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    public void login(@NonNull final String username) {
        this.username = username;
    }

    public void logout() {
        this.username = null;
    }

    @Override
    public void prepareRequest(@NonNull final HttpRequestBuilder requestBuilder) {
        if (this.username == null) {
            return;
        }

        this.authenticationProvider.authenticateRequest(this.username, requestBuilder);
    }
}
