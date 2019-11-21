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

package org.garnishtest.steps.restclient.manager;

import org.garnishtest.steps.vars.scenario_attrs.ScenarioAttribute;
import lombok.NonNull;

import java.net.http.HttpResponse;

public class ApiClientResponseManager {

    @NonNull private static final ScenarioAttribute<HttpResponse<String>> SESSION_ATTR_RESPONSE =
        ScenarioAttribute.create();
    @NonNull private static final ScenarioAttribute<String> SESSION_ATTR_BODY = ScenarioAttribute.create();

    public void setResponse(@NonNull final HttpResponse<String> response) {
        SESSION_ATTR_RESPONSE.setValue(response);
        SESSION_ATTR_BODY.setValue(response.body());
    }

    @NonNull public HttpResponse<String> getResponse() {
        return SESSION_ATTR_RESPONSE.getRequiredValue();
    }

    @NonNull
    public String getBody() {
        return SESSION_ATTR_BODY.getRequiredValue();
    }

}
