package com.mobiquityinc.mobit.modules.generic.exception_utils

import java.io.PrintWriter
import java.io.StringWriter

fun <T : Throwable?> T.toStringWithStacktrace(): String {
    if (this == null) {
        return "null"
    }

    val result = StringWriter()

    this.printStackTrace(
            PrintWriter(result)
    )

    return result.toString()
}