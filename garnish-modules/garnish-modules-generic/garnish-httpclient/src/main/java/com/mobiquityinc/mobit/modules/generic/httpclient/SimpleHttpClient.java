package com.mobiquityinc.mobit.modules.generic.httpclient;

import com.mobiquityinc.mobit.modules.generic.httpclient.executor.HttpRequestExecutor;
import com.mobiquityinc.mobit.modules.generic.httpclient.model.HttpMethod;
import com.mobiquityinc.mobit.modules.generic.httpclient.util.HttpUrlUtils;
import io.mola.galimatias.URL;
import lombok.NonNull;

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

    public HttpRequestBuilder request(@NonNull final HttpMethod method,
                                      @NonNull final String url) {
        final URL absoluteUrl = HttpUrlUtils.makeAbsoluteUrl(this.baseUrl, url);

        return new HttpRequestBuilderImpl(this.requestExecutor, method, absoluteUrl);
    }

    private URL parseUrl(@NonNull final String stringUrl)  {
        try {
            return URL.parse(stringUrl);
        } catch (final Exception e) {
            throw new IllegalArgumentException("failed to parse [" + stringUrl + "] as URL", e);
        }
    }

}
