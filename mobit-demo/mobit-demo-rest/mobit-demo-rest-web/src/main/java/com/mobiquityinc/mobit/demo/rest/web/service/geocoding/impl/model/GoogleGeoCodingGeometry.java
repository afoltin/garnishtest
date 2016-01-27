package com.mobiquityinc.mobit.demo.rest.web.service.geocoding.impl.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class GoogleGeoCodingGeometry {

    private final GoogleGeoCodingLocation location;

    @JsonCreator
    public GoogleGeoCodingGeometry(@JsonProperty("location") final GoogleGeoCodingLocation location) {
        this.location = location;
    }

    public GoogleGeoCodingLocation getLocation() {
        return this.location;
    }

}
