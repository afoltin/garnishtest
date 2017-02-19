package org.garnishtest.modules.generic.httpclient.model.body;

import org.apache.http.HttpEntity;

public interface HttpRequestBody {

    HttpEntity createEntity();

}
