package com.mobiquityinc.mobit.steps.vars.resource_files_vars;

import cucumber.api.java.en.Given;
import lombok.NonNull;

public final class ResourceFilesVariablesSteps {

    @Given("^the resource files prefix '([^']+)'$")
    public void set_resource_files_prefix(@NonNull final String resourceFilesPrefix) {
        ResourceFilesVariables.setResourceFilesPrefix(resourceFilesPrefix);
    }

}
