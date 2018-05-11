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

package org.garnishtest.modules.generic.httpclient.model;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;

import javax.annotation.concurrent.Immutable;
import java.util.List;

@Immutable
public final class HttpResponse {

    private final int statusCode;
    @NonNull private final ImmutableMap<String, ImmutableList<String>> headers;
    @NonNull private final String body;

    public HttpResponse(final int statusCode,
                        @NonNull final ImmutableMap<String, ImmutableList<String>> headers,
                        @NonNull final String body) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    @NonNull
    public Optional<List<String>> getHeaderValues(@NonNull final String headerName) {
        return Optional.<List<String>>fromNullable(
                this.headers.get(headerName)
        );
    }

    @NonNull
    public Optional<String> getFirstHeaderValue(@NonNull final String headerName) {
        final Optional<List<String>> optionalHeaderValues = getHeaderValues(headerName);

        if (optionalHeaderValues.isPresent()) {
            final List<String> headerValues = optionalHeaderValues.get();
            if (!headerValues.isEmpty()) {
                return Optional.of(
                        headerValues.get(0)
                );
            }
        }

        return Optional.absent();
    }

    @NonNull
    public ImmutableMap<String, ImmutableList<String>> getAllHeaders() {
        return this.headers;
    }

    @NonNull
    public String getBodyAsString() {
        return this.body;
    }
}
