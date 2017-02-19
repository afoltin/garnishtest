package org.garnishtest.modules.generic.uri.parser

import org.garnishtest.modules.generic.uri.Uri
import org.garnishtest.modules.generic.uri.builder.path
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner::class)
class UriParserTest {

    @Test
    @Parameters
    fun test(uriToParse: String, expectedUri: Uri) {
        val actualUri = UriParser.parse(uriToParse)

        assertEquals(expectedUri, actualUri)
    }

    fun parametersForTest() = arrayOf(
            arrayOf(""                                                 , path("").build()),
            arrayOf("/"                                                , path("/").build()),
            arrayOf("/a/longer/path"                                   , path("/a/longer/path").build()),
            arrayOf("/a/longer/path?"                                  , path("/a/longer/path").build()),
            arrayOf("/a/longer/path?p"                                 , path("/a/longer/path").param("p").build()),
            arrayOf("/a/longer/path?p="                                , path("/a/longer/path").param("p").build()),
            arrayOf("/a/longer/path?p=v"                               , path("/a/longer/path").param("p", "v").build()),
            arrayOf("/a/longer/path?p=v&"                              , path("/a/longer/path").param("p", "v").build()),
            arrayOf("/a/longer/path?p1=v1&p2"                          , path("/a/longer/path").param("p1", "v1").param("p2").build()),
            arrayOf("/a/longer/path?p1=v1&p2="                         , path("/a/longer/path").param("p1", "v1").param("p2").build()),
            arrayOf("/a/longer/path?p1=v1&p2=v2"                       , path("/a/longer/path").param("p1", "v1").param("p2", "v2").build()),
            arrayOf("/a/longer/path?p1=v1&p2=v2&"                      , path("/a/longer/path").param("p1", "v1").param("p2", "v2").build()),
            arrayOf("/a/longer/path?p1=v11&p1=v12&p1=v13&p2=v21&p2=v22", path("/a/longer/path").param("p1", "v11", "v12", "v13").param("p2", "v21", "v22").build()),
            arrayOf("/a/longer/path?p%3F%26%3D%2F+=v%3F%26%3D%2F+"     , path("/a/longer/path").param("p?&=/ ", "v?&=/ ").build())
    )

}