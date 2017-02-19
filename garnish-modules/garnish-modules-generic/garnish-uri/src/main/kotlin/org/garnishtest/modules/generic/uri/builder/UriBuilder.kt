package org.garnishtest.modules.generic.uri.builder

import org.garnishtest.modules.generic.uri.Uri
import lombok.NonNull
import javax.annotation.concurrent.NotThreadSafe

@NotThreadSafe
class UriBuilder(val path: String) {

    private val queryBuilder = UriQueryBuilder()

    fun param(@NonNull name: String,
              vararg values: String): UriBuilder {
        queryBuilder.param(name, *values)

        return this
    }

    fun build(): Uri {
        return Uri(
                this.path,
                queryBuilder.build()
        )
    }

}

fun path(path: String) = UriBuilder(path)
