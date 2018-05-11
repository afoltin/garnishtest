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

package org.garnishtest.modules.generic.uri.builder

import org.garnishtest.modules.generic.uri.UriQuery
import org.garnishtest.modules.generic.uri.util.MultiMapBuilder
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
