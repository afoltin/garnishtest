package org.garnishtest.modules.generic.uri.parser

import org.garnishtest.modules.generic.uri.Uri
import org.garnishtest.modules.generic.uri.UriQuery

object UriParser {

    fun parse(uri: String): Uri {
        val firstIndexOfQuestionMark = uri.indexOf('?')

        if (firstIndexOfQuestionMark == -1) {
            // no query parameters
            return Uri(
                    uri,
                    UriQuery.Companion.empty())
        } else if (firstIndexOfQuestionMark == uri.length - 1) {
            // ignore trailing question mark
            return Uri(
                    uri.substring(0, uri.length - 1),
                    UriQuery.Companion.empty())
        } else {
            // includes query parameters
            val path = uri.substring(0, firstIndexOfQuestionMark)
            val queryString = uri.substring(firstIndexOfQuestionMark + 1)

            val query = UriQueryParser.parseQuery(queryString)

            return Uri(path, query)
        }
    }

}
