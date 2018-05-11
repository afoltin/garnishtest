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

import org.garnishtest.modules.generic.uri.Uri
import org.garnishtest.modules.generic.uri.builder.path
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner::class)
class UriSerializeTest {

    @Test
    @Parameters
    fun test(uriToSerialize: Uri, expectedUri: String) {
        val serializedUri = UriSerializer.serialize(uriToSerialize)

        assertEquals(expectedUri, serializedUri)
    }

    fun parametersForTest() = arrayOf(
            arrayOf(path("").build()                                                                         , ""),
            arrayOf(path("/").build()                                                                        , "/"),
            arrayOf(path("/a/longer/path").build()                                                           , "/a/longer/path"),
            arrayOf(path("/a/longer/path").param("p").build()                                                , "/a/longer/path?p"),
            arrayOf(path("/a/longer/path").param("p", "v").build()                                           , "/a/longer/path?p=v"),
            arrayOf(path("/a/longer/path").param("p1", "v1").param("p2").build()                             , "/a/longer/path?p1=v1&p2"),
            arrayOf(path("/a/longer/path").param("p1", "v1").param("p2", "v2").build()                       , "/a/longer/path?p1=v1&p2=v2"),
            arrayOf(path("/a/longer/path").param("p1", "v11", "v12", "v13").param("p2", "v21", "v22").build(), "/a/longer/path?p1=v11&p1=v12&p1=v13&p2=v21&p2=v22"),
            arrayOf(path("/a/longer/path").param("p?&=/ ", "v?&=/ ").build()                                 , "/a/longer/path?p%3F%26%3D%2F+=v%3F%26%3D%2F+")
    )

}