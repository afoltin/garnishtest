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

package org.garnishtest.modules.generic.function_value_matcher.functions;

import com.google.common.base.Strings;

import javax.annotation.Nullable;

public class EmptinessChecker {

    public boolean isNull(@Nullable final String actualValue) {
        return actualValue == null;
    }

    public boolean isNotNull(@Nullable final String actualValue) {
        return !isNull(actualValue);
    }

    public boolean isEmpty(@Nullable final String actualValue) {
        return Strings.isNullOrEmpty(actualValue);
    }

    public boolean isNotEmpty(@Nullable final String actualValue) {
        return !isEmpty(actualValue);
    }

}
