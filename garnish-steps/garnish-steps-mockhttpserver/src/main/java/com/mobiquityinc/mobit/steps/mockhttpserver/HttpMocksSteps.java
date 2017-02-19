package com.mobiquityinc.mobit.steps.mockhttpserver;

import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.mobiquityinc.mobit.modules.generic.springutils.ClasspathUtils;
import com.mobiquityinc.mobit.steps.mockhttpserver.server.HttpMockServerManager;
import com.mobiquityinc.mobit.steps.vars.resource_files_vars.ResourceFilesVariables;
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
