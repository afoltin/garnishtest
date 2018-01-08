package org.garnishtest.demo.rest_complex.web.web.controllers.users.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public final class CreateUserResponse {

    private final long userId;

    @JsonCreator
    public CreateUserResponse(@JsonProperty("id") final long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return this.userId;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }

}
