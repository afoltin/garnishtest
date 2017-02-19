package org.garnishtest.demo.rest.web.service.geocoding.impl.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public final class GoogleGeoCodingResults {

    private final List<GoogleGeoCodingResult> results;

    @JsonCreator
    public GoogleGeoCodingResults(@JsonProperty("results") final List<GoogleGeoCodingResult> results) {
        this.results = results;
    }

    public List<GoogleGeoCodingResult> getResults() {
        return results;
    }
}
