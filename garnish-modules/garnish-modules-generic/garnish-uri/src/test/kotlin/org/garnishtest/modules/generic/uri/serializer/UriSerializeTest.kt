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