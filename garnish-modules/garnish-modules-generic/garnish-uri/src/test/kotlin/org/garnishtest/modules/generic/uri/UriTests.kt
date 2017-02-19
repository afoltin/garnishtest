package org.garnishtest.modules.generic.uri

import org.garnishtest.modules.generic.uri.parser.UriParserTest
import org.garnishtest.modules.generic.uri.serializer.UriSerializeTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
        UriParserTest::class,
        UriSerializeTest::class
                   )
class UriTests
