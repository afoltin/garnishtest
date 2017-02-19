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
