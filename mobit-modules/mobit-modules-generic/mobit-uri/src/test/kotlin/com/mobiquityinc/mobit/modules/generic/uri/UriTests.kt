package com.mobiquityinc.mobit.modules.generic.uri

import com.mobiquityinc.mobit.modules.generic.uri.parser.UriParserTest
import com.mobiquityinc.mobit.modules.generic.uri.serializer.UriSerializeTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    UriParserTest::class,
    UriSerializeTest::class
)
class UriTests
