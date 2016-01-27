package com.mobiquityinc.mobit.modules.generic.httpclient.request_preparer;

import com.mobiquityinc.mobit.modules.generic.httpclient.HttpRequestBuilder;
import lombok.NonNull;

public interface HttpRequestPreparer {

    void prepareRequest(@NonNull HttpRequestBuilder requestBuilder);

}
