package com.mobiquityinc.mobit.modules.generic.httpclient.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mobiquityinc.mobit.modules.generic.httpclient.model.body.HttpRequestBody;
import io.mola.galimatias.URL;
import lombok.NonNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
public final class HttpRequest {

    @NonNull private final HttpMethod method;
    @NonNull private final URL url;
    @NonNull private final ImmutableMap<String, ImmutableList<String>> headers;
    @Nullable private final HttpRequestBody body;

    public HttpRequest(@NonNull final HttpMethod method,
                       @NonNull final URL url,
                       @NonNull final ImmutableMap<String, ImmutableList<String>> headers,
                       @Nullable final HttpRequestBody body) {
        this.method = method;
        this.url = url;
        this.headers = headers;
        this.body = body;
    }

    @NonNull
    public HttpMethod getMethod() {
        return this.method;
    }

    @NonNull
    public URL getUrl() {
        return this.url;
    }

    @NonNull
    public ImmutableMap<String, ImmutableList<String>> getHeaders() {
        return this.headers;
    }

    @Nullable
    public HttpRequestBody getBody() {
        return this.body;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
