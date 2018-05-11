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

package org.garnishtest.modules.generic.uri.util

import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableMap
import java.util.LinkedHashMap
import javax.annotation.concurrent.NotThreadSafe

@NotThreadSafe
class MultiMapBuilder<K, V> {

    private val map = LinkedHashMap<K, MutableList<V>>()

    fun setValue(key: K, value: V) {
        val values = arrayListOf<V>()
        values.add(value)

        this.map.put(key, values)
    }

    fun removeValue(key: K) {
        this.map.remove(key)
    }

    fun addValue(key: K, value: V) {
        val existingValues = this.map[key]

        if (existingValues != null) {
            existingValues.add(value)
        } else {
            val newValues = arrayListOf<V>()

            newValues.add(value)

            this.map.put(key, newValues)
        }
    }

    fun addAllValues(key: K, vararg values: V) {
        for (value in values) {
            addValue(key, value)
        }
    }

    fun addAllValues(key: K, values: List<V>) {
        for (value in values) {
            addValue(key, value)
        }
    }

    val isEmpty: Boolean
        get() = this.map.isEmpty()

    fun build(): ImmutableMap<K, ImmutableList<V>> {
        val result = ImmutableMap.builder<K, ImmutableList<V>>()

        for ((key, values) in this.map) {
            result.put(
                    key,
                    ImmutableList.copyOf(values)
            )
        }

        return result.build()
    }
}
