package com.mobiquityinc.mobit.modules.it.test_utils_json.compare;

import com.mobiquityinc.mobit.modules.generic.function_value_matcher.FunctionValueMatcher;
import lombok.NonNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.skyscreamer.jsonassert.comparator.DefaultComparator;

import javax.annotation.Nullable;
import java.util.Objects;

public final class FunctionsAwareJsonComparator extends DefaultComparator {

    private static final java.lang.String FUNCTION_PREFIX = "@";
    private static final java.lang.String ESCAPED_FUNCTION_PREFIX = "\\" + FUNCTION_PREFIX;

    @NonNull
    private final FunctionValueMatcher functionValueMatcher;

    public FunctionsAwareJsonComparator(@NonNull final FunctionValueMatcher functionValueMatcher) {
        super(JSONCompareMode.LENIENT);

        this.functionValueMatcher = functionValueMatcher;
    }

    @Override
    public void compareValues(final java.lang.String prefix, final Object expectedValue, final Object actualValue, final JSONCompareResult result) throws JSONException {
        if (expectedValue instanceof Number && actualValue instanceof Number) {
            if (numbersDifferent((Number) expectedValue, (Number) actualValue)) {
                result.fail(prefix, expectedValue, actualValue);
            }
        } else if (expectedValue.getClass().isAssignableFrom(actualValue.getClass())) {
            if (expectedValue instanceof JSONArray) {
                compareJSONArray(prefix, (JSONArray) expectedValue, (JSONArray) actualValue, result);
            } else if (expectedValue instanceof JSONObject) {
                compareJSON(prefix, (JSONObject) expectedValue, (JSONObject) actualValue, result);
            } else {
                compareObjectValues(prefix, expectedValue, actualValue, result);
            }
        } else {
            compareObjectValues(prefix, expectedValue, actualValue, result);
        }
    }

    // Suppressing rule "Floating point numbers should not be tested for equality"
    //
    // This is safe because we don't do any math with these numbers
    // (so no problem with rounding): we simply parse the 2 JSON files and compare.
    @SuppressWarnings("squid:S1244")
    private boolean numbersDifferent(final Number expectedValue, final Number actualValue) {
        return expectedValue.doubleValue() != actualValue.doubleValue();
    }

    private void compareObjectValues(@Nullable final String prefix,
                                     @Nullable final Object expectedValue,
                                     @Nullable final Object actualValue,
                                     @NonNull final JSONCompareResult result) {
        if (expectedValue instanceof String) {
            compareExpectedString(prefix, (String) expectedValue, actualValue, result);
        } else {
            compareDefault(prefix, expectedValue, actualValue, result);
        }
    }

    private void compareExpectedString(@Nullable final String prefix,
                                       @NonNull final String expectedValue,
                                       @Nullable final Object actualValue,
                                       @NonNull final JSONCompareResult result) {
        if (expectedValue.startsWith(FUNCTION_PREFIX)) {
            compareExpectedFunction(prefix, expectedValue, actualValue, result);

        } else if (expectedValue.startsWith(ESCAPED_FUNCTION_PREFIX)) {
            compareEscapedFunction(prefix, expectedValue, actualValue, result);
        } else {
            compareDefault(prefix, expectedValue, actualValue, result);
        }
    }

    private void compareExpectedFunction(@Nullable final String prefix,
                                         @NonNull final String expectedFunction,
                                         @Nullable final Object actualValue,
                                         @NonNull final JSONCompareResult result) {// it's a function
        final String expression = expectedFunction.substring(FUNCTION_PREFIX.length());

        if (!this.functionValueMatcher.matches(nullableToString(actualValue), expression)) {
            result.fail(prefix, expectedFunction, actualValue);
        }
    }

    /** compare ignoring the escape character */
    private void compareEscapedFunction(@Nullable final String prefix,
                                        @NonNull final String escapedFunction,
                                        @Nullable final Object actualValue,
                                        @NonNull final JSONCompareResult result) {
        final String expectedValueWithoutEscape = escapedFunction.substring(ESCAPED_FUNCTION_PREFIX.length() - FUNCTION_PREFIX.length());

        compareDefault(prefix, expectedValueWithoutEscape, actualValue, result);
    }

    private void compareDefault(@Nullable final String prefix,
                                @Nullable final Object expectedValue,
                                @Nullable final Object actualValue,
                                @NonNull final JSONCompareResult result) {
        if (!Objects.equals(expectedValue, actualValue)) {
            result.fail(prefix, expectedValue, actualValue);
        }
    }

    @Nullable
    private static String nullableToString(@Nullable final Object object) {
        if (object == null) {
            return null;
        }

        return String.valueOf(object);
    }

}
