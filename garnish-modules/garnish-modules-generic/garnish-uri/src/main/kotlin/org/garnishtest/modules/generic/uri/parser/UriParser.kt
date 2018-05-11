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
