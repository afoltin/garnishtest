package org.garnishtest.modules.generic.function_value_matcher.function_eval;

public interface ValueMatcher {

    void setValue(String value);
    void setFunctionObjects(Object... functionObjects);

    boolean matches();

}
