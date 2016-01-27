package com.mobiquityinc.mobit.modules.generic.function_value_matcher.functions;

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
