package com.mobiquityinc.mobit.modules.generic.httpclient.request_preparer.impl;

import com.mobiquityinc.mobit.modules.generic.httpclient.HttpRequestBuilder;
import com.mobiquityinc.mobit.modules.generic.httpclient.request_preparer.HttpRequestPreparer;
import lombok.NonNull;

public final class NoOpHttpRequestPreparer implements HttpRequestPreparer {

    private static final NoOpHttpRequestPreparer INSTANCE = new NoOpHttpRequestPreparer();

    private NoOpHttpRequestPreparer() { }

    public static NoOpHttpRequestPreparer instance() {
        return INSTANCE;
    }

    @Override
    public void prepareRequest(@NonNull final HttpRequestBuilder requestBuilder) {
        // no-op = no operation (do nothing)
    }
}
