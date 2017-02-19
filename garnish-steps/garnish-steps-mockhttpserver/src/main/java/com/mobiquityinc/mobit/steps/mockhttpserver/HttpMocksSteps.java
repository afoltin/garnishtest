package com.mobiquityinc.mobit.steps.mockhttpserver;

import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.mobiquityinc.mobit.modules.generic.springutils.ClasspathUtils;
import com.mobiquityinc.mobit.modules.it.test_utils_json.JsonUtils;
import com.mobiquityinc.mobit.steps.mockhttpserver.server.HttpMockServerManager;
import com.mobiquityinc.mobit.steps.vars.resource_files_vars.ResourceFilesVariables;
import cucumber.api.java.en.Given;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HttpMocksSteps {

    @NonNull private final HttpMockServerManager httpMockServerManager;

    @Autowired
    public HttpMocksSteps(@NonNull final HttpMockServerManager httpMockServerManager) {
        this.httpMockServerManager = httpMockServerManager;
    }

    @Given("^http mocks from '(.+)'$")
    public void given_http_mocks_from_classpath_resource(@NonNull final String httpMocksFile) {
        final String serializedMappings = ClasspathUtils.loadFromClasspath(ResourceFilesVariables.getResourceFilesPrefix() + httpMocksFile);

        final List<StubMapping> mappings = JsonUtils.parseStringToList(serializedMappings, StubMapping.class);

        httpMockServerManager.reset();
        httpMockServerManager.register(mappings);
    }

}
