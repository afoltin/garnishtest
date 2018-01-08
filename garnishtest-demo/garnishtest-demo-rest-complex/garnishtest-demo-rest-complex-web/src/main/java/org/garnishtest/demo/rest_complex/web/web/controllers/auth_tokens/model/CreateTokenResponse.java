package org.garnishtest.demo.rest_complex.web.web.controllers.auth_tokens.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class CreateTokenResponse {

    @NonNull
    private final String token;

    @JsonCreator
    public CreateTokenResponse(@NonNull @JsonProperty("token") final String token) {
        this.token = token;
    }

    @NonNull
    public String getToken() {
        return this.token;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }

}
