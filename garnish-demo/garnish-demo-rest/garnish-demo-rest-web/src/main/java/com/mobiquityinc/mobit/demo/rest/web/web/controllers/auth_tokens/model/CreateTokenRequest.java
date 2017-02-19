package com.mobiquityinc.mobit.demo.rest.web.web.controllers.auth_tokens.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class CreateTokenRequest {

    @NonNull
    private final String password;

    @JsonCreator
    public CreateTokenRequest(@NonNull @JsonProperty("password") final String password) {
        this.password = password;
    }

    @NonNull
    public String getPassword() {
        return this.password;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }

}
