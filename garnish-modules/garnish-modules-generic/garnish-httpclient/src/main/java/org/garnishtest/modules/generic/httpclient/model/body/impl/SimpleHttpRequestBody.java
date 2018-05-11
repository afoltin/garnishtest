/*
 * Copyright 2016-2018, Garnish.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

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
