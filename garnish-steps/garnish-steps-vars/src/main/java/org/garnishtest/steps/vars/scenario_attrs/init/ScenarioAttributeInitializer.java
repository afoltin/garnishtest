package org.garnishtest.steps.vars.scenario_attrs.init;

import javax.annotation.Nullable;

public interface ScenarioAttributeInitializer<T> {

    @Nullable
    T getInitialValue();

}
