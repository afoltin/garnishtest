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

package org.garnishtest.steps.vars.resource_files_vars;

import org.garnishtest.steps.vars.scenario_attrs.ScenarioAttribute;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;

public final class ResourceFilesVariables {

    @NonNull private static final ScenarioAttribute<String> RESOURCE_FILES_PREFIX = ScenarioAttribute.create();

    private ResourceFilesVariables() {
    }

    public static void setResourceFilesPrefix(@Nullable final String resourceFilesPrefix) {
        RESOURCE_FILES_PREFIX.setValue(resourceFilesPrefix);
    }

    @NonNull
    public static String getResourceFilesPrefix() {
        return StringUtils.defaultString(
                RESOURCE_FILES_PREFIX.getValue()
        );
    }

}
