package org.garnishtest.steps.restclient.spring;

import org.garnishtest.modules.generic.httpclient.HttpRequestBuilder;
import org.garnishtest.modules.generic.httpclient.request_preparer.HttpRequestPreparer;
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
