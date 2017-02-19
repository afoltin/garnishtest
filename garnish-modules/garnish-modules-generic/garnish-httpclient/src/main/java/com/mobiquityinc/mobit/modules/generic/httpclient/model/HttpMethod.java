package com.mobiquityinc.mobit.modules.generic.httpclient.model;

public enum HttpMethod {

    GET     (false),
    DELETE  (false),
    HEAD    (false),
    OPTIONS (false),
    TRACE   (false),

    PUT     (true),
    POST    (true),
    PATCH   (true),
    ;

    private final boolean canHaveBody;

    HttpMethod(final boolean canHaveBody) {
        this.canHaveBody = canHaveBody;
    }

    public boolean canHaveBody() {
        return this.canHaveBody;
    }
}
