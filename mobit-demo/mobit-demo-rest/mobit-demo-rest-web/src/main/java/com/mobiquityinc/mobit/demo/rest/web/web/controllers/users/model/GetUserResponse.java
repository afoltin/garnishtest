package com.mobiquityinc.mobit.demo.rest.web.web.controllers.users.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class GetUserResponse {

    private final long id;
    private final String name;
    private final String username;
    private final GetUserAddress address;

    @JsonCreator
    public GetUserResponse(@JsonProperty("id") final long id,
                           @JsonProperty("name") final String name,
                           @JsonProperty("username") final String username,
                           @JsonProperty("address") final GetUserAddress address) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.address = address;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getUsername() {
        return this.username;
    }

    public GetUserAddress getAddress() {
        return address;
    }
}
