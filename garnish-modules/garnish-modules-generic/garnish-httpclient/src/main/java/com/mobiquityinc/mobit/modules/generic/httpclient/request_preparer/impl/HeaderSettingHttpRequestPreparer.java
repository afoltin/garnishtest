package com.mobiquityinc.mobit.modules.generic.httpclient.request_preparer.impl;

import com.mobiquityinc.mobit.modules.generic.httpclient.HttpRequestBuilder;
import com.mobiquityinc.mobit.modules.generic.httpclient.request_preparer.HttpRequestPreparer;
import lombok.NonNull;

public final class HeaderSettingHttpRequestPreparer implements HttpRequestPreparer {

    @NonNull private final String name;
    @NonNull private final String value;

    public HeaderSettingHttpRequestPreparer(@NonNull final String name, @NonNull final String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public void prepareRequest(@NonNull final HttpRequestBuilder requestBuilder) {
        requestBuilder.setHeader(this.name, this.value);
    }
}
