package org.garnishtest.modules.generic.httpclient.model.body.impl;

import org.garnishtest.modules.generic.httpclient.model.body.HttpRequestBody;
import lombok.NonNull;
import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;

public final class MultipartHttpRequestBody implements HttpRequestBody {

    @NonNull
    private final MultipartEntityBuilder multipartEntityBuilder;

    public MultipartHttpRequestBody(@NonNull final MultipartEntityBuilder multipartEntityBuilder) {
        this.multipartEntityBuilder = multipartEntityBuilder;
    }

    @Override
    public HttpEntity createEntity() {
        return this.multipartEntityBuilder.build();
    }

}
