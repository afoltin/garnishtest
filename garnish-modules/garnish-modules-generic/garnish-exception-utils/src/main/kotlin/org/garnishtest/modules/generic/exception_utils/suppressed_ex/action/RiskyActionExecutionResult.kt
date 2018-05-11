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

package org.garnishtest.modules.generic.exception_utils.suppressed_ex.action

import org.garnishtest.modules.generic.exception_utils.toStringWithStacktrace
import lombok.NonNull
import javax.annotation.concurrent.Immutable

@Immutable
class RiskyActionExecutionResult<out R> private constructor(private val succeeded: Boolean,
                                                            val result: R?,
                                                            val exception: Exception?) {

    companion object {
        fun <R> success(): RiskyActionExecutionResult<R> {
            return RiskyActionExecutionResult(succeeded = true, result = null, exception = null)
        }

        fun <R> success(result: R?): RiskyActionExecutionResult<R> {
            return RiskyActionExecutionResult(succeeded = true, result = result, exception = null)
        }

        fun <R> failure(@NonNull exception: Exception): RiskyActionExecutionResult<R> {
            return RiskyActionExecutionResult(succeeded = false, result = null, exception = exception)
        }
    }

    fun succeeded(): Boolean {
        return this.succeeded
    }

    fun failed(): Boolean {
        return !succeeded()
    }

    override fun toString(): String {
        return "RiskyActionExecutionResult{" +
                "result=$result" +
                ", succeeded=$succeeded" +
                ", exception=${this.exception.toStringWithStacktrace()}" +
                "}"
    }

}
