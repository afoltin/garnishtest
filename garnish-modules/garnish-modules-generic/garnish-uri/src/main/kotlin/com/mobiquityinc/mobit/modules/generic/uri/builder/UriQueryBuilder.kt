package com.mobiquityinc.mobit.modules.generic.uri.builder

import com.mobiquityinc.mobit.modules.generic.uri.UriQuery
import com.mobiquityinc.mobit.modules.generic.uri.util.MultiMapBuilder
import javax.annotation.concurrent.NotThreadSafe

@NotThreadSafe
class UriQueryBuilder {

    private val queryParametersBuilder = MultiMapBuilder<String, String>()

    fun param(name: String, vararg values: String): UriQueryBuilder {
        if (values.isEmpty()) {
            this.queryParametersBuilder.addValue(name, "")
        } else {
            for (value in values) {
                this.queryParametersBuilder.addValue(name, value)
            }
        }

        return this
    }

    fun build(): UriQuery {
        if (this.queryParametersBuilder.isEmpty) {
            return UriQuery.empty()
        } else {
            return UriQuery(
                    queryParametersBuilder.build()
            )
        }
    }

    override fun toString(): String {
        return build().toString()
    }

    fun toStringWithInitialQuestionMark(): String {
        return build().toStringWithInitialQuestionMark()
    }

    fun toStringWithoutInitialQuestionMark(): String {
        return build().toStringWithoutInitialQuestionMark()
    }

}

// convenience method to be imported statically
fun queryParams() = UriQueryBuilder()
