package com.mobiquityinc.mobit.demo.rest.web.service.security;

import com.mobiquityinc.mobit.demo.rest.web.dao.model.Address;
import lombok.NonNull;

import javax.annotation.concurrent.Immutable;
import java.math.BigDecimal;

@Immutable
public final class SecurityUserAddress {

    @NonNull private final String textualAddress;
    @NonNull private BigDecimal latitude;
    @NonNull private BigDecimal longitude;

    public SecurityUserAddress(@NonNull final String textualAddress,
                               @NonNull final BigDecimal latitude,
                               @NonNull final BigDecimal longitude) {
        this.textualAddress = textualAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static SecurityUserAddress fromDbAddress(@NonNull final Address dbAddress) {
        return new SecurityUserAddress(
                dbAddress.getTextualAddress(),
                dbAddress.getLatitude(),
                dbAddress.getLongitude()
        );
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
