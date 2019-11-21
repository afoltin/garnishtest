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

package org.garnishtest.modules.generic.httpclient.executor;

import lombok.NonNull;
import org.garnishtest.modules.generic.httpclient.HttpRequestBuilderImpl;
import org.garnishtest.modules.generic.httpclient.request_preparer.HttpRequestPreparer;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class HttpRequestExecutor {

    @NonNull private final HttpClient client;
    @NonNull private final HttpRequestPreparer httpRequestPreparer;

    public HttpRequestExecutor(@NonNull final HttpClient client,
        @NonNull final HttpRequestPreparer httpRequestPreparer) {
        this.client = client;
        this.httpRequestPreparer = httpRequestPreparer;
    }

    public HttpResponse<String> execute(@NonNull final HttpRequestBuilderImpl requestBuilder)
        throws HttpRequestExecutorException {
        try {
            this.httpRequestPreparer.prepareRequest(requestBuilder);
        } catch (final Exception e) {
            throw new HttpRequestExecutorException(
                "failed to prepare request [" + requestBuilder.build() + "]", e);
        }
        final HttpRequest request = requestBuilder.build();
        try {
            return this.client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (final Exception e) {
            throw new HttpRequestExecutorException("failed to execute request [" + request + "]",
                e);
        }
    }
}
