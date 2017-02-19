package org.garnishtest.modules.generic.httpclient.model.body.impl;

import org.garnishtest.modules.generic.httpclient.model.body.HttpRequestBody;
import lombok.NonNull;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import javax.annotation.Nullable;

public final class SimpleHttpRequestBody implements HttpRequestBody {

    @NonNull private final String content;
    @Nullable private final ContentType contentType;

    public SimpleHttpRequestBody(@NonNull final String content,
                                 @Nullable final ContentType contentType) {
        this.content = content;
        this.contentType = contentType;
    }

    @Override
    public HttpEntity createEntity() {
        final StringEntity entity = new StringEntity(this.content, this.contentType);

        if (this.contentType == null) {
            entity.setContentType((String) null);
        }

        return entity;
    }
}
