package com.mobiquityinc.mobit.modules.generic.httpclient.model.body;

import org.apache.http.HttpEntity;

public interface HttpRequestBody {

    HttpEntity createEntity();

}
