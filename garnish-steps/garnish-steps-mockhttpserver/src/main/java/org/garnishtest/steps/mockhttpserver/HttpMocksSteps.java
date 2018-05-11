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

package org.garnishtest.steps.mockhttpserver;

import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import org.garnishtest.modules.generic.springutils.ClasspathUtils;
import org.garnishtest.steps.mockhttpserver.server.HttpMockServerManager;
import org.garnishtest.steps.vars.resource_files_vars.ResourceFilesVariables;
import cucumber.api.java.en.Given;
import lombok.NonNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class HttpMocksSteps {

    @NonNull private final HttpMockServerManager httpMockServerManager;

    @Autowired
    public HttpMocksSteps(@NonNull final HttpMockServerManager httpMockServerManager) {
        this.httpMockServerManager = httpMockServerManager;
    }

    @Given("^http mocks from '(.+)'$")
    public void given_http_mocks_from_classpath_resource(@NonNull final String httpMocksFile) throws JSONException {
        final String serializedMappings = ClasspathUtils.loadFromClasspath(ResourceFilesVariables.getResourceFilesPrefix() + httpMocksFile);

        final List<StubMapping> mappings = new ArrayList<>();

        final JSONArray jsonArray = new JSONArray(serializedMappings);
        for (int i = 0; i < jsonArray.length(); i++) {
            mappings.add(
                    StubMapping.buildFrom(
                            jsonArray.getJSONObject(i).toString()
                    )
            );
        }

        this.httpMockServerManager.reset();
        this.httpMockServerManager.register(mappings);
    }

}
