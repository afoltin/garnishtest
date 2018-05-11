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

package org.garnishtest.demo.rest_complex.web.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;

public final class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = createObjectMapper();

    private JsonUtils() { }

    @NonNull
    private static ObjectMapper createObjectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper;
    }

    public static <T> T parseJson(@NonNull final String jsonToParse,
                                  @NonNull final Class<T> resultClass) {
        try {
            return OBJECT_MAPPER.readValue(jsonToParse, resultClass);
        } catch (final Exception e) {
            throw new JsonUtilsException("failed to parse [" + jsonToParse + "] as class [" + resultClass + "]", e);
        }
    }

    public static <T> T parseJson(@NonNull final String jsonToParse,
                                  @NonNull final TypeReference<T> resultTypeReference) {
        try {
            return OBJECT_MAPPER.readValue(jsonToParse, resultTypeReference);
        } catch (final Exception e) {
            throw new JsonUtilsException("failed to parse [" + jsonToParse + "] as type reference [" + resultTypeReference + "]", e);
        }
    }
}
