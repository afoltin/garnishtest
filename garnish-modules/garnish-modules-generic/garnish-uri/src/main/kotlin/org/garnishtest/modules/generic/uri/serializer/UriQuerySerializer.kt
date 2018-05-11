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

package org.garnishtest.modules.generic.uri.serializer

import org.garnishtest.modules.generic.uri.UriQuery
import org.garnishtest.modules.generic.uri.encoder.UriEncoder

object UriQuerySerializer {

    fun serialize(query: UriQuery): String {
        val result = StringBuilder()

        serialize(result, query)

        return result.toString()
    }

    fun serialize(result: StringBuilder,
                  query: UriQuery) {
        val queryParameters = query.parameters

        if (!queryParameters.isEmpty()) {
            result.append("?")
        }

        var firstValue = true
        for ((paramName, paramValues) in queryParameters) {
            if (firstValue) {
                firstValue = false
            } else {
                result.append("&")
            }

            serializeQueryParameterValues(result, paramName, paramValues)
        }
    }

    private fun serializeQueryParameterValues(result: StringBuilder,
                                              paramName: String,
                                              paramValues: Collection<String>) {
        val encodedParamName = UriEncoder.encodeQueryParameter(paramName)

        var firstValue = true
        for (paramValue in paramValues) {
            val encodedParamValue = UriEncoder.encodeQueryParameter(paramValue)

            if (firstValue) {
                firstValue = false
            } else {
                result.append("&")
            }

            result.append(encodedParamName)

            if (!encodedParamValue.isEmpty()) {
                result.append("=")
                result.append(encodedParamValue)
            }
        }
    }

}
