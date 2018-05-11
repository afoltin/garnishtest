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

package org.garnishtest.modules.generic.exception_utils.suppressed_ex

import org.garnishtest.modules.generic.exception_utils.suppressed_ex.action.RiskyAction
import org.garnishtest.modules.generic.exception_utils.suppressed_ex.action.RiskyActionExecutionResult
import lombok.NonNull
import java.lang.Throwable
import javax.annotation.concurrent.NotThreadSafe

@NotThreadSafe
class ExceptionTrackingExecutor {

    private val actionFailures = mutableListOf<RiskyActionExecutionResult<*>>()

    fun <R> execute(@NonNull action: RiskyAction<R>): RiskyActionExecutionResult<R> {
        try {
            val result = action.execute()

            return RiskyActionExecutionResult.success(result)
        } catch (e: Exception) {
            val failure = RiskyActionExecutionResult.failure<R>(e)
            actionFailures.add(failure)

            return failure
        }
    }

    @Throws(ExceptionTrackingExecutorException::class)
    fun throwIfNeeded() {
        if (actionFailures.size == 0) {
            return
        }

        val message = ""
        val cause = actionFailures[0].exception
        val enableSuppression = true
        val writableStackTrace = false

        val exceptionToThrow = ExceptionTrackingExecutorException(
                message,
                cause!!,
                enableSuppression,
                writableStackTrace)

        if (actionFailures.size > 1) {
            actionFailures.subList(1, actionFailures.size - 1).forEach { failure ->
                // cast to gain access to the "addSuppressed" method that is otherwise not supported by Kotlin (which currently - version 1.0.6 - only targets JDK 6)
                @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
                (exceptionToThrow as Throwable).addSuppressed(failure.exception)
            }
        }

        throw exceptionToThrow
    }

}
