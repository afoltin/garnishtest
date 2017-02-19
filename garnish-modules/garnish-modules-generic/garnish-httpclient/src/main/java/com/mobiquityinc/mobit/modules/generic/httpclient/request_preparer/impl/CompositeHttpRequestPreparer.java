package com.mobiquityinc.mobit.modules.generic.httpclient.request_preparer.impl;

import com.mobiquityinc.mobit.modules.generic.httpclient.HttpRequestBuilder;
import com.mobiquityinc.mobit.modules.generic.httpclient.request_preparer.HttpRequestPreparer;
import lombok.NonNull;

import java.util.List;

public final class CompositeHttpRequestPreparer implements HttpRequestPreparer {

    @NonNull private final List<HttpRequestPreparer> preparers;

    public CompositeHttpRequestPreparer(@NonNull final List<HttpRequestPreparer> requestPreparers) {
        this.preparers = requestPreparers;
    }

    @Override
    public void prepareRequest(@NonNull final HttpRequestBuilder requestBuilder) {
        for (final HttpRequestPreparer preparer : this.preparers) {
            preparer.prepareRequest(requestBuilder);
        }
    }
}
