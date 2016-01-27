package com.mobiquityinc.mobit.modules.it.test_utils_json.compare;

import com.mobiquityinc.mobit.modules.generic.function_value_matcher.FunctionValueMatcher;
import com.mobiquityinc.mobit.modules.it.test_utils_json.JsonUtils;
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
