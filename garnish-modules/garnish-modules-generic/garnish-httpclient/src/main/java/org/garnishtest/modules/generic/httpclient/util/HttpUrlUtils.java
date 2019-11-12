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

package org.garnishtest.modules.generic.httpclient.util;

import io.mola.galimatias.URL;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

public final class HttpUrlUtils {

    private HttpUrlUtils() {
    }

    public static boolean isAbsoluteUrl(@NonNull final String url) {
        return url.startsWith("http://") || url.startsWith("https://");
    }

    @NonNull public static URL parseUrl(@NonNull final String url) {
        try {
            return URL.parse(url);
        } catch (final Exception e) {
            throw new IllegalArgumentException("[" + url + "] is not a valid URL", e);
        }
    }

    @NonNull
    public static URL makeAbsoluteUrl(@NonNull final URL baseUrl, @NonNull final String url) {
        if (isAbsoluteUrl(url)) {
            return parseUrl(url);
        }

        try {
            final String baseUrlWithoutTrailingSlash =
                StringUtils.removeEnd(baseUrl.toString(), "/");
            final String urlWithoutLeadingSlash = StringUtils.removeStart(url, "/");

            return URL.parse(baseUrlWithoutTrailingSlash + "/" + urlWithoutLeadingSlash);
        } catch (final Exception e) {
            throw new IllegalArgumentException("[" + url + "] is not a valid URL", e);
        }
    }
}
