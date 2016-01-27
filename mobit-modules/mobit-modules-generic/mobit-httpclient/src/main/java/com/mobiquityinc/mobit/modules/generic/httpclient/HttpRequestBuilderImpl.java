package com.mobiquityinc.mobit.modules.generic.httpclient;

import com.mobiquityinc.mobit.modules.generic.httpclient.executor.HttpRequestExecutor;
import com.mobiquityinc.mobit.modules.generic.httpclient.model.HttpMethod;
import com.mobiquityinc.mobit.modules.generic.httpclient.model.HttpRequest;
import com.mobiquityinc.mobit.modules.generic.httpclient.model.HttpResponse;
import com.mobiquityinc.mobit.modules.generic.httpclient.model.body.HttpRequestBody;
import com.mobiquityinc.mobit.modules.generic.httpclient.model.body.impl.MultipartHttpRequestBody;
import com.mobiquityinc.mobit.modules.generic.httpclient.model.body.impl.SimpleHttpRequestBody;
import com.mobiquityinc.mobit.modules.generic.uri.util.MultiMapBuilder;
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
