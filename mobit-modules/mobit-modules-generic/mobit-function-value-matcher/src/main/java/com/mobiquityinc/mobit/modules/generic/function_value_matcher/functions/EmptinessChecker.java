package com.mobiquityinc.mobit.modules.generic.function_value_matcher.functions;

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
