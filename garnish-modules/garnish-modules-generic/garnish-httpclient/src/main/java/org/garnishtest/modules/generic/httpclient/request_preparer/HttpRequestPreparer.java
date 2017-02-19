package org.garnishtest.modules.generic.httpclient.request_preparer;

import org.garnishtest.modules.generic.httpclient.HttpRequestBuilder;
import lombok.NonNull;

public interface HttpRequestPreparer {

    void prepareRequest(@NonNull HttpRequestBuilder requestBuilder);

}
