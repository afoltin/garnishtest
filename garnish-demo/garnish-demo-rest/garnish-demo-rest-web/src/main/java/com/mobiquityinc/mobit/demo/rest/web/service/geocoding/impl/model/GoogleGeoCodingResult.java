package com.mobiquityinc.mobit.demo.rest.web.service.geocoding.impl.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class GoogleGeoCodingResult {

    private final GoogleGeoCodingGeometry geometry;

    @JsonCreator
    public GoogleGeoCodingResult(@JsonProperty("geometry") final GoogleGeoCodingGeometry geometry) {
        this.geometry = geometry;
    }

    public GoogleGeoCodingGeometry getGeometry() {
        return this.geometry;
    }

}
