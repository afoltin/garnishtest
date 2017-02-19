package org.garnishtest.modules.generic.httpclient.util;

import io.mola.galimatias.URL;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

public final class HttpUrlUtils {

    private HttpUrlUtils() { }

    public static boolean isAbsoluteUrl(@NonNull final String url) {
        return url.startsWith("http://")
               || url.startsWith("https://");
    }

    @NonNull
    public static URL parseUrl(@NonNull final String url) {
        try {
            return URL.parse(url);
        } catch (final Exception e) {
            throw new IllegalArgumentException("[" + url + "] is not a valid URL", e);
        }
    }

    @NonNull
    public static URL makeAbsoluteUrl(@NonNull final URL baseUrl,
                                         @NonNull final String url) {
        if (isAbsoluteUrl(url)) {
            return parseUrl(url);
        }

        try {
            final String baseUrlWithoutTrailingSlash = StringUtils.removeEnd(baseUrl.toString(), "/");
            final String urlWithoutLeadingSlash = StringUtils.removeStart(url, "/");

            return URL.parse(baseUrlWithoutTrailingSlash + "/" + urlWithoutLeadingSlash);
        } catch (final Exception e) {
            throw new IllegalArgumentException("[" + url + "] is not a valid URL", e);
        }
    }
}
