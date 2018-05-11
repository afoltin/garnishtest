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

package org.garnishtest.modules.generic.uri

import com.google.common.base.Optional
import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableMap
import org.garnishtest.modules.generic.uri.serializer.UriQuerySerializer
import org.garnishtest.modules.generic.uri.util.MultiMapBuilder
import lombok.NonNull
import java.util.*
import javax.annotation.concurrent.Immutable

@Immutable
class UriQuery(@NonNull parameters: Map<String, List<String>>) {

    // todo: insure that the parameters are "canonicalized" so that equals and hashcode work fine (I think cleanupParameters already does it, but we need to check)

    val parameters: ImmutableMap<String, ImmutableList<String>>

    init {
        fun cleanupValues(values: List<String>): ImmutableList<String> {
            if (values.isEmpty()) {
                return EMPTY_VALUES
            } else {
                return ImmutableList.copyOf(values)
            }
        }

        /**
         * Makes the given parameters acceptable for use by this class.
         *
         *  - skips null keys
         *  - skips null values
         *  - replaces empty values list with one entry: the empty string
         *  - makes value list immutable
         */
        fun cleanupParameters(originalParameters: Map<String, List<String>>): ImmutableMap<String, ImmutableList<String>> {
            val cleanParametersBuilder = MultiMapBuilder<String, String>()

            for ((key, values) in originalParameters) {
                cleanParametersBuilder.addAllValues(key, cleanupValues(values))
            }

            return cleanParametersBuilder.build()
        }

        this.parameters = cleanupParameters(parameters)
    }


    @NonNull
    fun getParameterValues(@NonNull parameterName: String): Optional<ImmutableList<String>> {
        return Optional.fromNullable(
                this.parameters[parameterName])
    }

    fun getFirstParameterValue(@NonNull parameterName: String): String? {
        val values = this.parameters[parameterName]
        if (values == null || values.isEmpty()) {
            return null
        }

        val firstValue = values[0]

        return firstValue
    }

    val isEmpty: Boolean
        get() = this.parameters.isEmpty()

    override fun equals(other: Any?): Boolean{
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as UriQuery

        return Objects.equals(this.parameters, other.parameters)
    }

    override fun hashCode(): Int {
        return Objects.hash(
                this.parameters
                           )
    }

    override fun toString(): String {
        return toStringWithInitialQuestionMark()
    }

    fun toStringWithInitialQuestionMark(): String {
        return UriQuerySerializer.serialize(this)
    }

    fun toStringWithoutInitialQuestionMark(): String {
        val maybeWithQuestionMark = toStringWithInitialQuestionMark()

        if (maybeWithQuestionMark.startsWith("?")) {
            return maybeWithQuestionMark.substring(1)
        } else {
            return maybeWithQuestionMark
        }
    }

    companion object {
        private val EMPTY_VALUES = ImmutableList.of("")
        private val EMPTY = UriQuery(
                emptyMap<String, List<String>>())

        @JvmStatic
        fun empty(): UriQuery {
            return EMPTY
        }

        @JvmStatic
        fun fromSingleValuedParameters(singleValuedParameters: Map<String, String>): UriQuery {
            val multiValuedParameters = singleValuedToMultiValuedParameters(singleValuedParameters)

            return UriQuery(multiValuedParameters)
        }

        private fun singleValuedToMultiValuedParameters(singleValuedParameters: Map<String, String>): Map<String, List<String>> {
            val multiValuedParameters = LinkedHashMap<String, List<String>>(singleValuedParameters.size)

            for ((key, value) in singleValuedParameters) {
                multiValuedParameters.put(
                        key,
                        listOf(value)
                )
            }

            return multiValuedParameters
        }
    }
}