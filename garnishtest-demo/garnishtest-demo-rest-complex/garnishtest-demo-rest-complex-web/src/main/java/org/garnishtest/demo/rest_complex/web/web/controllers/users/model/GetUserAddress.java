package org.garnishtest.demo.rest_complex.web.web.controllers.users.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;

import javax.annotation.concurrent.Immutable;
import java.math.BigDecimal;

@Immutable
public final class GetUserAddress {

    private final String textualAddress;
    private final BigDecimal latitude;
    private final BigDecimal longitude;

    @JsonCreator
    public GetUserAddress(@NonNull @JsonProperty("textualAddress") final String textualAddress,
                          @NonNull @JsonProperty("latitude") final BigDecimal latitude,
                          @NonNull @JsonProperty("longitude") final BigDecimal longitude) {
        this.textualAddress = textualAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getTextualAddress() {
        return textualAddress;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }
}
