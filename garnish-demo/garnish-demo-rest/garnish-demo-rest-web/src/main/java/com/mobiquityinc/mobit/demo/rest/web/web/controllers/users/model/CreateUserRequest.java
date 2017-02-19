package com.mobiquityinc.mobit.demo.rest.web.web.controllers.users.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class CreateUserRequest {

    @NonNull private final String name;
    @NonNull private final String username;
    @NonNull private final String password;

    @NonNull private final String address;

    @JsonCreator
    public CreateUserRequest(@NonNull @JsonProperty("name") final String name,
                             @NonNull @JsonProperty("username") final String username,
                             @NonNull @JsonProperty("password") final String password,
                             @NonNull @JsonProperty("address") final String address) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.address = address;
    }

    @NonNull
    public String getName() {
        return this.name;
    }

    @NonNull
    public String getUsername() {
        return this.username;
    }

    @NonNull
    public String getPassword() {
        return this.password;
    }

    @NonNull
    public String getAddress() {
        return this.address;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }

}
