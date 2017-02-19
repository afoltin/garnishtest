package org.garnishtest.demo.rest.web.utils;

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
