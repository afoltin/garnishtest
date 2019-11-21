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

package org.garnishtest.modules.generic.httpclient;

import io.mola.galimatias.URL;
import lombok.NonNull;
import org.garnishtest.modules.generic.httpclient.executor.HttpRequestExecutor;
import org.garnishtest.modules.generic.httpclient.model.HttpMethod;
import org.garnishtest.modules.generic.httpclient.util.HttpUrlUtils;

public final class SimpleHttpClient {

    // todo: integration tests for this class (using WireMock)
    // todo: test to automatically un-gzip responses

    @NonNull private final URL baseUrl;
    @NonNull private final HttpRequestExecutor requestExecutor;

    public SimpleHttpClient(@NonNull final String baseUrl,
        @NonNull final HttpRequestExecutor requestExecutor) {
        this.baseUrl = parseUrl(baseUrl);
        this.requestExecutor = requestExecutor;
    }

    public HttpRequestBuilder request(@NonNull final HttpMethod method, @NonNull final String url) {
        final URL absoluteUrl = HttpUrlUtils.makeAbsoluteUrl(this.baseUrl, url);

        return new HttpRequestBuilderImpl(this.requestExecutor, method, absoluteUrl);
    }

    private URL parseUrl(@NonNull final String stringUrl) {
        try {
            return URL.parse(stringUrl);
        } catch (final Exception e) {
            throw new IllegalArgumentException("failed to parse [" + stringUrl + "] as URL", e);
        }
    }

}
