package org.garnishtest.modules.generic.springutils;

import lombok.NonNull;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.nio.charset.StandardCharsets;

public final class ClasspathUtils {

    private ClasspathUtils() {
        throw new UnsupportedOperationException("utility classes should not be instantiated");
    }

    @NonNull
    public static String loadFromClasspath(@NonNull final String classpathLocation) {
        try {
            final Resource resource = new ClassPathResource(classpathLocation);

            if (!resource.exists()) {
                throw new IllegalArgumentException(
                        "resource at classpath location [" + classpathLocation + "] does not exist"
                );
            }

            return IOUtils.toString(
                    resource.getInputStream(),
                    StandardCharsets.UTF_8
            );
        } catch (final Exception e) {
            throw new RuntimeException("failed to load resource at classpath location [" + classpathLocation + "]", e);
        }
    }

}
