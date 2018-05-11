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

import org.garnishtest.modules.generic.httpclient.executor.HttpRequestExecutor;
import org.garnishtest.modules.generic.httpclient.model.HttpMethod;
import org.garnishtest.modules.generic.httpclient.model.HttpRequest;
import org.garnishtest.modules.generic.httpclient.model.HttpResponse;
import org.garnishtest.modules.generic.httpclient.model.body.HttpRequestBody;
import org.garnishtest.modules.generic.httpclient.model.body.impl.MultipartHttpRequestBody;
import org.garnishtest.modules.generic.httpclient.model.body.impl.SimpleHttpRequestBody;
import org.garnishtest.modules.generic.uri.util.MultiMapBuilder;
import io.mola.galimatias.URL;
import lombok.NonNull;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import javax.annotation.Nullable;
import java.util.List;

public final class HttpRequestBuilderImpl implements HttpRequestBuilder {

    @NonNull private final HttpRequestExecutor httpRequestExecutor;

    @NonNull private final HttpMethod method;
    @NonNull private final URL url;

    @NonNull private final MultiMapBuilder<String, String> headers = new MultiMapBuilder<>();
    @Nullable private HttpRequestBody body;

    public HttpRequestBuilderImpl(@NonNull final HttpRequestExecutor httpRequestExecutor,
                                  @NonNull final HttpMethod method,
                                  @NonNull final URL url) {
        this.httpRequestExecutor = httpRequestExecutor;

        this.method = method;
        this.url = url;
    }

    /** like {@link #addHeader(String, String)}, but previous values for this header are removed */
    @NonNull
    @Override
    public HttpRequestBuilder setHeader(@NonNull final String name,
                                        @NonNull final String value) {
        this.headers.setValue(name, value);

        return this;
    }

    /** like {@link #setHeader(String, String)}, but previous values for this header are preserved */
    @NonNull
    @Override
    public HttpRequestBuilder addHeader(@NonNull final String name,
                                        @NonNull final String value) {
        this.headers.addValue(name, value);

        return this;
    }

    @NonNull
    @Override
    public HttpRequestBuilder addHeader(@NonNull final String name,
                                        @Nullable final String... values) {
        this.headers.addAllValues(name, values);

        return this;
    }

    @NonNull
    @Override
    public HttpRequestBuilder addHeader(@NonNull final String key,
                                        @NonNull final List<String> values) {
        this.headers.addAllValues(key, values);

        return this;
    }

    @Override
    @NonNull
    public HttpRequestBuilder body(@NonNull final String body) {
        return body(body, null);
    }

    @Override
    @NonNull
    public HttpRequestBuilder body(@NonNull final String body,
                                   @Nullable final ContentType contentType) {
        this.body = new SimpleHttpRequestBody(body, contentType);

        return this;
    }

    @Override
    @NonNull
    public HttpRequestBuilder body(@NonNull final MultipartEntityBuilder body) {
        this.body = new MultipartHttpRequestBody(body);

        return this;
    }

    @Override
    @NonNull
    public HttpResponse execute() {
        return this.httpRequestExecutor.execute(this);
    }

    public HttpRequest build() {
        return new HttpRequest(
                this.method, this.url, this.headers.build(), this.body
        );
    }
}
