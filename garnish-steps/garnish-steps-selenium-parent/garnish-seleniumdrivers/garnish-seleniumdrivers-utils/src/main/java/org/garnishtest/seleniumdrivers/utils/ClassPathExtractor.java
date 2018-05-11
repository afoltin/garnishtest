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
