package com.mobiquityinc.mobit.modules.it.test_utils_json.load;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.io.CharStreams;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public final class JsonLoader {

    @NonNull private final ClassLoader classLoader;
    @NonNull private final ObjectMapper objectMapper;

    public JsonLoader(@Nullable final SimpleModule... modules) {
        this(
                Thread.currentThread().getContextClassLoader(),
                modules
        );
    }

    public JsonLoader(@NonNull final ClassLoader classLoader,
                      @Nullable final SimpleModule... modules) {
        this.classLoader = classLoader;

        this.objectMapper = createObjectMapper(modules);
    }

    private ObjectMapper createObjectMapper(@Nullable final SimpleModule... modules) {
        final ObjectMapper objectMapper = new ObjectMapper();

        if (modules != null) {
            for (final SimpleModule module : modules) {
                objectMapper.registerModule(module);
            }
        }

        return objectMapper;
    }

    public <T> T loadJson(final String classpathLocation, final Class<T> classOfRootObject) {
        try {
            return tryToLoadJson(classpathLocation, classOfRootObject);
        } catch (final Exception e) {
            throw new JsonLoaderException(
                    "could not load JSON from classpath resource at [" + classpathLocation + "]",
                    e
            );
        }
    }

    private <T> T tryToLoadJson(final String classpathLocation, final Class<T> classOfRootObject) throws IOException {
        final String content = readClasspathResource(classpathLocation);

        return this.objectMapper.readValue(content, classOfRootObject);
    }

    public <T> T loadJson(final String classpathLocation, final TypeReference<T> typeReference) {
        try {
            return tryToLoadJson(classpathLocation, typeReference);
        } catch (final Exception e) {
            throw new JsonLoaderException(
                    "could not load JSON from classpath resource at [" + classpathLocation + "]",
                    e
            );
        }
    }

    private <T> T tryToLoadJson(final String classpathLocation, final TypeReference<T> typeReference)
            throws IOException {
        final String content = readClasspathResource(classpathLocation);

        return this.objectMapper.readValue(content, typeReference);
    }


    private String readClasspathResource(final String classpathLocation) throws IOException {
        final InputStream inputStream = this.classLoader.getResourceAsStream(classpathLocation);

        if (inputStream == null) {
            throw new RuntimeException("could not find classpath resource at [" + classpathLocation + "]");
        }

        try (final InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return CharStreams.toString(reader);
        }
    }


}
