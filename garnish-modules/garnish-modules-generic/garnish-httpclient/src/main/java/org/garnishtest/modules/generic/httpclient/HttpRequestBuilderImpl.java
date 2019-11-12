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

import com.google.common.net.HttpHeaders;
import io.mola.galimatias.URL;
import lombok.NonNull;
import org.garnishtest.modules.generic.httpclient.executor.HttpRequestExecutor;
import org.garnishtest.modules.generic.httpclient.model.HttpMethod;
import org.garnishtest.modules.generic.httpclient.model.body.impl.MultiPartBodyPublisher;
import org.springframework.http.MediaType;
import org.springframework.util.MimeType;

import javax.annotation.Nullable;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class HttpRequestBuilderImpl implements HttpRequestBuilder {

    @NonNull private final HttpRequest.Builder builder;
    @NonNull private final HttpRequestExecutor httpRequestExecutor;

    @NonNull private final HttpMethod method;

    public HttpRequestBuilderImpl(@NonNull final HttpRequestExecutor httpRequestExecutor,
        @NonNull final HttpMethod method, @NonNull final URL url) {
        this.httpRequestExecutor = httpRequestExecutor;

        this.method = method;
        this.builder = HttpRequest.newBuilder(URI.create(url.toString()));
    }

    /**
     * like {@link #addHeader(String, String)}, but previous values for this header are removed
     */
    @NonNull @Override public HttpRequestBuilder setHeader(@NonNull final String name,
        @NonNull final String value) {
        this.builder.setHeader(name, value);

        return this;
    }

    /**
     * like {@link #setHeader(String, String)}, but previous values for this header are preserved
     */
    @NonNull @Override public HttpRequestBuilder addHeader(@NonNull final String name,
        @NonNull final String value) {
        this.builder.header(name, value);

        return this;
    }

    @NonNull @Override public HttpRequestBuilder addHeader(@NonNull final String name,
        @Nullable final String... values) {
        for (String value : values) {
            addHeader(name, value);
        }
        return this;
    }

    @NonNull @Override public HttpRequestBuilder addHeader(@NonNull final String key,
        @NonNull final List<String> values) {
        for (String value : values) {
            addHeader(key, value);
        }
        return this;
    }

    @Override @NonNull public HttpRequestBuilder body(@NonNull final String body) {
        return body(body, null);
    }

    @Override @NonNull
    public HttpRequestBuilder body(@NonNull final String body, @Nullable final MimeType mimeType) {
        if (this.method.canHaveBody()) {
            this.builder.method(method.name(), HttpRequest.BodyPublishers.ofString(body));
        } else {
            this.builder.method(method.name(), HttpRequest.BodyPublishers.noBody());
        }
        if (mimeType != null) {
            setHeader(HttpHeaders.CONTENT_TYPE, mimeType.toString());
        }
        return this;
    }

    @Override @NonNull
    public HttpRequestBuilder body(@NonNull final MultiPartBodyPublisher publisher) {
        Map<String, String> params = new HashMap<>();
        params.put("boundary", publisher.getBoundary());
        setHeader(HttpHeaders.CONTENT_TYPE,
            new MediaType(MediaType.MULTIPART_FORM_DATA, params).toString());

        this.builder.POST(publisher.build());

        return this;
    }

    @Override @NonNull public HttpResponse<String> execute() {
        return this.httpRequestExecutor.execute(this);
    }

    public HttpRequest build() {
        return this.builder.build();
    }
}
