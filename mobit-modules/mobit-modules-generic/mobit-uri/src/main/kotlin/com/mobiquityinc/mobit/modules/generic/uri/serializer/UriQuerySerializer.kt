package com.mobiquityinc.mobit.modules.generic.uri.serializer

import com.mobiquityinc.mobit.modules.generic.uri.UriQuery
import com.mobiquityinc.mobit.modules.generic.uri.encoder.UriEncoder

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
