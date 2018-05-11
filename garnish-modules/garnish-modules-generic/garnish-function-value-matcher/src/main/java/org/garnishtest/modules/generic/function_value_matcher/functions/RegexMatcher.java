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

package org.garnishtest.modules.generic.function_value_matcher.functions;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.NonNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import java.util.regex.Pattern;

@ThreadSafe
public class RegexMatcher {

    private static final int MAX_CACHE_SIZE = 10000;
    private static final LoadingCache<String, Pattern> PATTERNS_CACHE = createPatternsCache(MAX_CACHE_SIZE);

    private static LoadingCache<String, Pattern> createPatternsCache(final int maxCacheSize) {
        final CacheLoader<String, Pattern> cacheLoader = new CacheLoader<String, Pattern>() {
            @Override
            public Pattern load(@Nonnull @NonNull final String regex) {
                return Pattern.compile(regex);
            }
        };

        return CacheBuilder.<String, Pattern>newBuilder()
                .maximumSize(maxCacheSize)
                .build(cacheLoader);
    }

    public boolean matchesRegex(@Nullable final String actualValue,
                                @Nullable final String regex) {
        if (actualValue == null) {
            return regex == null;
        }
        if (regex == null) {
            return false;
        }

        try {
            final Pattern pattern = PATTERNS_CACHE.get(regex);

            return pattern.matcher(actualValue).matches();
        } catch (final Exception e) {
            throw new IllegalArgumentException(
                    "failed to check if regex [" + regex + "] matches [" + actualValue + "]",
                    e
            );
        }
    }

}
