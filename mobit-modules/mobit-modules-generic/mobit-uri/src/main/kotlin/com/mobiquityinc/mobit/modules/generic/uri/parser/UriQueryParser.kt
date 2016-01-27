package com.mobiquityinc.mobit.modules.generic.uri.parser

import com.google.common.base.Splitter
import com.mobiquityinc.mobit.modules.generic.uri.UriQuery
import com.mobiquityinc.mobit.modules.generic.uri.encoder.UriEncoder
import com.mobiquityinc.mobit.modules.generic.uri.util.MultiMapBuilder
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
