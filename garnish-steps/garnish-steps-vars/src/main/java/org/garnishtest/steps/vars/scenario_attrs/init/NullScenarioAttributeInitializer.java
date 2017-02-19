package org.garnishtest.steps.vars.scenario_attrs.init;

import javax.annotation.Nullable;

public class NullScenarioAttributeInitializer<T> implements ScenarioAttributeInitializer<T> {

    @SuppressWarnings("rawtypes")
    private static final NullScenarioAttributeInitializer INSTANCE = new NullScenarioAttributeInitializer();

    @SuppressWarnings("unchecked")
    public static <T> NullScenarioAttributeInitializer<T> instance() {
        return INSTANCE;
    }

    @Override
    @Nullable
    public T getInitialValue() {
        return null;
    }

}
