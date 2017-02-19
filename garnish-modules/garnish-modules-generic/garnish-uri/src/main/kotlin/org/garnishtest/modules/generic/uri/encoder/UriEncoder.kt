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
