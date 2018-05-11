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

package org.garnishtest.modules.it.test_utils_json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public final class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = createObjectMapper();

    private JsonUtils() {
        throw new UnsupportedOperationException("utility classes should not be instantiated");
    }

    private static ObjectMapper createObjectMapper() {
        final ObjectMapper mapper = new ObjectMapper();

        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper;
    }

    public static <T> T parseString(@NonNull final String jsonString,
                                    @NonNull final Class<T> resultClass) {
        try {
            return OBJECT_MAPPER.readValue(jsonString, resultClass);
        } catch (final Exception e) {
            throw new RuntimeException(
                    "an error occurred trying to parse" +
                    " JSON string [" + jsonString + "]" +
                    ", resultClass=[" + resultClass + "]",
                    e
            );
        }
    }

    public static <T> List<T> parseStringToList(@NonNull final String jsonString,
                                                @NonNull final Class<T> resultElementClass) {
        try {
            final CollectionType type = OBJECT_MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, resultElementClass);

            return OBJECT_MAPPER.readValue(jsonString, type);
        } catch (final Exception e) {
            throw new RuntimeException(
                    "an error occurred trying to parse" +
                    " JSON string [" + jsonString + "]" +
                    ", resultElementClass=[" + resultElementClass + "]",
                    e
            );
        }
    }

    public static <T> T parseString(@NonNull final String jsonString,
                                    @NonNull final TypeReference<T> resultTypeReference) {
        try {
            return OBJECT_MAPPER.readValue(jsonString, resultTypeReference);
        } catch (final Exception e) {
            throw new RuntimeException(
                    "an error occurred trying to parse" +
                    " JSON string [" + jsonString + "]" +
                    ", resultTypeReference=[" + resultTypeReference.getType() + "]",
                    e
            );
        }
    }


    @NonNull
    public static <T> String serialize(@NonNull final T object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (final Exception e) {
            throw new RuntimeException(
                    "an error occurred trying to serialize " +
                    " object [" + object + "]",
                    e
            );
        }
    }

    /**
     * Make the JSON valid by removing non-valid JSON constructs (like comments), always using double quotes, etc.
     */
    public static String makeValidJson(@NonNull final String jsonContentWithComments) {
        final Object parsedJson = JsonUtils.parseString(jsonContentWithComments, new TypeReference<Object>() {});

        final String jsonContentWithoutComments = JsonUtils.serialize(parsedJson);

        return jsonContentWithoutComments;
    }
}
