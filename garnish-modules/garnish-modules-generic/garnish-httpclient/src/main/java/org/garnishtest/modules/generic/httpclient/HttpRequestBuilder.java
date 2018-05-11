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

package org.garnishtest.modules.generic.httpclient;

import org.garnishtest.modules.generic.httpclient.model.HttpResponse;
import lombok.NonNull;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import javax.annotation.Nullable;
import java.util.List;

public interface HttpRequestBuilder {

    @NonNull HttpRequestBuilder setHeader(@NonNull String name, @NonNull String value);
    @NonNull HttpRequestBuilder addHeader(@NonNull String name, @NonNull String value);
    @NonNull HttpRequestBuilder addHeader(@NonNull String name, @Nullable String... values);
    @NonNull HttpRequestBuilder addHeader(@NonNull String key, @NonNull List<String> values);

    @NonNull HttpRequestBuilder body(@NonNull String body);
    @NonNull HttpRequestBuilder body(@NonNull String body, @NonNull final ContentType contentType);
    @NonNull HttpRequestBuilder body(@NonNull MultipartEntityBuilder body);

    @NonNull
    HttpResponse execute();
}
