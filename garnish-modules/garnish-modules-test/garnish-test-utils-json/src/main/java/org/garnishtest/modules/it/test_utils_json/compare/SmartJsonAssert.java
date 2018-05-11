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

package org.garnishtest.modules.it.test_utils_json.compare;

import org.garnishtest.modules.generic.function_value_matcher.FunctionValueMatcher;
import org.garnishtest.modules.it.test_utils_json.JsonUtils;
import lombok.NonNull;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareResult;

public final class SmartJsonAssert {

    @NonNull
    private final FunctionsAwareJsonComparator jsonComparator;

    public SmartJsonAssert(@NonNull final FunctionValueMatcher functionValueMatcher) {
        this.jsonComparator = new FunctionsAwareJsonComparator(functionValueMatcher);
    }

    public void assertEquals(final String expectedJson, final String actualJson) throws JSONException {
        final String validExpectedJson = JsonUtils.makeValidJson(expectedJson);
        final String validActualJson = JsonUtils.makeValidJson(actualJson);

        final JSONCompareResult compareResult = JSONCompare.compareJSON(validExpectedJson, validActualJson, this.jsonComparator);

        if (compareResult.failed()) {
            throw new AssertionError(compareResult.getMessage());
        }
    }

}
