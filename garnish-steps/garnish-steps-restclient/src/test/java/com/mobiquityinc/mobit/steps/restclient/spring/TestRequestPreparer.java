package com.mobiquityinc.mobit.steps.restclient.spring;

import com.mobiquityinc.mobit.modules.generic.httpclient.HttpRequestBuilder;
import com.mobiquityinc.mobit.modules.generic.httpclient.request_preparer.HttpRequestPreparer;
import lombok.NonNull;

public final class TestRequestPreparer implements HttpRequestPreparer {

    @NonNull private final String name;

    public TestRequestPreparer(final String name) {
        this.name = name;
    }

    @NonNull
    public String getName() {
        return this.name;
    }

    @Override
    public void prepareRequest(@NonNull final HttpRequestBuilder requestBuilder) {}

}
