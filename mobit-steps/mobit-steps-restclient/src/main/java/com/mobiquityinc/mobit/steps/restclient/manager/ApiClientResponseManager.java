package com.mobiquityinc.mobit.steps.restclient.manager;

import com.mobiquityinc.mobit.steps.vars.scenario_attrs.ScenarioAttribute;
import lombok.NonNull;
import com.mobiquityinc.mobit.modules.generic.httpclient.model.HttpResponse;

public class ApiClientResponseManager {

    @NonNull private static final ScenarioAttribute<HttpResponse> SESSION_ATTR_RESPONSE = ScenarioAttribute.create();
    @NonNull private static final ScenarioAttribute<String> SESSION_ATTR_BODY = ScenarioAttribute.create();

    public void setResponse(@NonNull final HttpResponse response) {
        SESSION_ATTR_RESPONSE.setValue(response);
        SESSION_ATTR_BODY.setValue(response.getBodyAsString());
    }

    @NonNull
    public HttpResponse getResponse() {
        return SESSION_ATTR_RESPONSE.getRequiredValue();
    }

    @NonNull
    public String getBody() {
        return SESSION_ATTR_BODY.getRequiredValue();
    }

}
