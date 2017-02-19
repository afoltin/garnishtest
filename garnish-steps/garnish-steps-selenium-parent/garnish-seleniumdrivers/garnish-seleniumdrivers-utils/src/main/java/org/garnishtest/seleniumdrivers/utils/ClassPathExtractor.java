package org.garnishtest.seleniumdrivers.utils;

import lombok.NonNull;
import org.apache.commons.io.IOUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ClassPathExtractor {

    @NonNull private final ClassLoader classLoader;

    public ClassPathExtractor() {
        this(
                Thread.currentThread().getContextClassLoader()
        );
    }

    public ClassPathExtractor(@NonNull final ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    private boolean classpathResourceExists(@NonNull final String executableClasspathLocation,
                                            @NonNull final ClassLoader classLoader) {
        return classLoader.getResource(executableClasspathLocation) != null;
    }

    @NonNull
    public Path extractToTemp(@NonNull final String classpathLocation,
                              @NonNull final String prefix) {
        if (!classpathResourceExists(classpathLocation, classLoader)) {
            throw new IllegalArgumentException(
                    "cannot find [" + classpathLocation + "]" +
                    " in the class path" +
                    " using classLoader [" + classLoader + "]"
            );
        }

        try {
            return tryToExtractToTemp(classpathLocation, prefix);
        } catch (final Exception e) {
            throw new ClasspathExtractorException(
                    "failed to extract" +
                    " class path resource [" + classpathLocation + "]" +
                    " (loader [" + classLoader + "])" +
                    " to the temp directory",
                    e
            );
        }
    }

    private Path tryToExtractToTemp(@NonNull final String classpathLocation,
                                    @NonNull final String prefix) throws IOException {
        final Path tempFile = Files.createTempFile(prefix, "");
        tempFile.toFile().deleteOnExit();

        try (final InputStream in = classLoader.getResourceAsStream(classpathLocation);
             final OutputStream out = new FileOutputStream(tempFile.toFile())) {
            IOUtils.copy(in, out);
        }

        return tempFile;
    }
}
