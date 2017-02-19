package com.mobiquityinc.mobit.steps.vars.resource_files_vars;

import com.mobiquityinc.mobit.steps.vars.scenario_attrs.ScenarioAttribute;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;

public final class ResourceFilesVariables {

    @NonNull private static final ScenarioAttribute<String> RESOURCE_FILES_PREFIX = ScenarioAttribute.create();


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
