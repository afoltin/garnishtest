package com.mobiquityinc.mobit.modules.generic.uri.serializer

import com.mobiquityinc.mobit.modules.generic.uri.Uri

object UriSerializer {

    fun serialize(uri: Uri): String {
        val result = StringBuilder()

        result.append(uri.path)

        UriQuerySerializer.serialize(result, uri.query)

        return result.toString()
    }

}
