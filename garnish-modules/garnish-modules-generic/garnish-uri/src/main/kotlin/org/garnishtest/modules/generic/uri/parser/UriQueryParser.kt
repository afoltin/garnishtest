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

package org.garnishtest.modules.generic.uri.parser

import com.google.common.base.Splitter
import org.garnishtest.modules.generic.uri.UriQuery
import org.garnishtest.modules.generic.uri.encoder.UriEncoder
import org.garnishtest.modules.generic.uri.util.MultiMapBuilder
import lombok.NonNull

object UriQueryParser {

    fun parseQuery(@NonNull queryString: String): UriQuery {
        val parametersBuilder = MultiMapBuilder<String, String>()

        val paramPairs = Splitter.on("&").split(queryString)
        for (paramPair in paramPairs) {
            if (paramPair.isEmpty()) {
                continue
            }
            val indexOfEquals = paramPair.indexOf('=')

            if (indexOfEquals == -1) {
                // no value
                parametersBuilder.addValue(
                        UriEncoder.decodeQueryParameter(paramPair),
                        ""
                )
            } else if (indexOfEquals == paramPair.length - 1) {
                // ignore trailing equals character
                parametersBuilder.addValue(
                        paramPair.substring(0, paramPair.length - 1),
                        ""
                )
            } else {
                // it has a value
                val encodedParamName = paramPair.substring(0, indexOfEquals)
                val encodedParamValue = paramPair.substring(indexOfEquals + 1)

                parametersBuilder.addValue(
                        UriEncoder.decodeQueryParameter(encodedParamName),
                        UriEncoder.decodeQueryParameter(encodedParamValue)
                )
            }
        }

        return UriQuery(
                parametersBuilder.build()
        )
    }

}
