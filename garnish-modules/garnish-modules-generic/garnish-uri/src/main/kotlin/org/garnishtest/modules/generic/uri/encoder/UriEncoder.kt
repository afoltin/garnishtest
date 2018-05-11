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

package org.garnishtest.modules.generic.uri.encoder

import com.google.common.net.UrlEscapers
import org.apache.commons.codec.net.URLCodec

object UriEncoder {

    private val URL_CODEC = URLCodec()
    private val URL_ENCODING = "UTF-8"

    /**
     * Encodes query parameters - should be used both for parameter names and parameter values.
     */
    fun encodeQueryParameter(queryParameter: String): String {
        try {
            return UrlEscapers.urlFormParameterEscaper().escape(queryParameter)
        } catch (e: Exception) {
            throw UriEncoderException("cannot encodeQueryParameter [$queryParameter]", e)
        }

    }

    /**
     * Decodes query parameters - should be used both for parameter names and parameter values.
     */
    fun decodeQueryParameter(queryParameter: String) = decode(queryParameter)

    private fun decode(text: String): String {
        try {
            return URL_CODEC.decode(text, URL_ENCODING)
        } catch (e: Exception) {
            throw UriEncoderException("cannot encode [$text]", e)
        }
    }

}
