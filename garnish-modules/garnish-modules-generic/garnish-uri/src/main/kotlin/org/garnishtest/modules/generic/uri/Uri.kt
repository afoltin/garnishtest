package org.garnishtest.modules.generic.uri

import org.garnishtest.modules.generic.uri.serializer.UriSerializer
import javax.annotation.concurrent.Immutable

@Immutable
data class Uri(val path: String, val query: UriQuery) {

    // todo: also parse absolute URLs (scheme, authorization (user/pass), domain, port)
    // todo: path builder (that encodes path segments)

    override fun toString(): String {
        return UriSerializer.serialize(this)
    }

}
