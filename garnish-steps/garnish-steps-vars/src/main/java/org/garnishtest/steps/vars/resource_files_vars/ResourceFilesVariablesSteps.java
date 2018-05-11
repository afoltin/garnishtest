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

import cucumber.api.java.en.Given;
import lombok.NonNull;

public final class ResourceFilesVariablesSteps {

    @Given("^the resource files prefix '([^']+)'$")
    public void set_resource_files_prefix(@NonNull final String resourceFilesPrefix) {
        ResourceFilesVariables.setResourceFilesPrefix(resourceFilesPrefix);
    }

}
