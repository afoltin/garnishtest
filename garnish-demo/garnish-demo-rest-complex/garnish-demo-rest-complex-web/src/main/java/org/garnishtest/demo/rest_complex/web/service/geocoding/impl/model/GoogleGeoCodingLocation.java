package org.garnishtest.demo.rest_complex.web.service.geocoding.impl.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.garnishtest.demo.rest_complex.web.service.geocoding.model.GeoLocation;
import lombok.NonNull;

import java.math.BigDecimal;

public final class GoogleGeoCodingLocation {

    @NonNull private final BigDecimal latitude;
    @NonNull private final BigDecimal longitude;

    @JsonCreator
    public GoogleGeoCodingLocation(@NonNull @JsonProperty("lat") final BigDecimal latitude,
                                   @NonNull @JsonProperty("lng") final BigDecimal longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @NonNull
    public BigDecimal getLatitude() {
        return this.latitude;
    }

    @NonNull
    public BigDecimal getLongitude() {
        return this.longitude;
    }

    @NonNull
    public GeoLocation getGeoLocation() {
        return new GeoLocation(this.latitude, this.longitude);
    }
}
