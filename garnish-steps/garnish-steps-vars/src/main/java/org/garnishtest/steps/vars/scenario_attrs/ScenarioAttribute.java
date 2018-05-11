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

package org.garnishtest.steps.vars.scenario_attrs;

import org.garnishtest.steps.vars.scenario_attrs.init.NullScenarioAttributeInitializer;
import org.garnishtest.steps.vars.scenario_attrs.init.ScenarioAttributeInitializer;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public final class ScenarioAttribute<T> {

    // todo: refactor this to use ThreadLocal variables

    @NonNull private static final List<ScenarioAttribute<?>> ATTRIBUTES= new ArrayList<>();

    @NonNull private final ScenarioAttributeInitializer<? extends T> initializer;
    private T value;

    /** use {@link #create()} or {@link #create(ScenarioAttributeInitializer)} instead of this constructor*/
    private ScenarioAttribute(@NonNull final ScenarioAttributeInitializer<? extends T> initializer) {
        this.initializer = initializer;
        this.value = initializer.getInitialValue();

        ATTRIBUTES.add(this);
    }

    /** convenience static factory method to not have to specify the angle brackets */
    public static <T> ScenarioAttribute<T> create() {
        return new ScenarioAttribute<>(NullScenarioAttributeInitializer.<T>instance());
    }

    /** convenience static factory method to not have to specify the angle brackets */
    public static <T> ScenarioAttribute<T> create(@NonNull final ScenarioAttributeInitializer<? extends T> initializer) {
        return new ScenarioAttribute<>(initializer);
    }

    public void reset() {
        this.value = this.initializer.getInitialValue();
    }

    public static void resetAll() {
        for (final ScenarioAttribute<?> attribute : ATTRIBUTES) {
            attribute.reset();
        }
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public T getValue() {
        return value;
    }

    @NonNull
    public T getRequiredValue() {
        final T value = getValue();
        if (value == null) {
            throw new IllegalStateException("required attribute has no value (maybe it has not been assigned yet, or it's value was removed)");
        }

        return value;
    }

    public void setValue(@Nullable final T value) {
        this.value = value;
    }

    public void removeValue() {
        this.value = null;
    }


}
