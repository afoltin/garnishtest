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
            throw new ClasspathUtilsException(
                "failed to load resource at classpath location [" + classpathLocation + "]", e);
        }
    }

}
