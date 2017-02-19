package org.garnishtest.demo.rest.web.service.geocoding.model;

import com.google.common.collect.Range;
import lombok.NonNull;

import javax.annotation.concurrent.Immutable;
import java.math.BigDecimal;
import java.util.Objects;

@Immutable
public final class GeoLocation {

    private static final int SCALE = 10;

    private static final Range<BigDecimal> LATITUDE_RANGE = Range.closed(
            new BigDecimal("-90").setScale(SCALE, BigDecimal.ROUND_UNNECESSARY),
            new BigDecimal("90").setScale(SCALE, BigDecimal.ROUND_UNNECESSARY)
    );
    private static final Range<BigDecimal> LONGITUDE_RANGE = Range.closed(
            new BigDecimal("-180.0").setScale(SCALE, BigDecimal.ROUND_UNNECESSARY),
            new BigDecimal("180.0").setScale(SCALE, BigDecimal.ROUND_UNNECESSARY)
    );

    @NonNull private final BigDecimal latitude;
    @NonNull private final BigDecimal longitude;

    public GeoLocation(@NonNull final BigDecimal latitude,
                       @NonNull final BigDecimal longitude) throws IllegalArgumentException {
        validate(latitude, longitude);

        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static void validate(@NonNull final BigDecimal latitude,
                                @NonNull final BigDecimal longitude) throws IllegalArgumentException {
        validateLatitude(latitude);
        validateLongitude(longitude);
    }

    public static void validateLatitude(@NonNull final BigDecimal latitude) {
        if (!LATITUDE_RANGE.contains(latitude)) {
            throw new IllegalArgumentException("latitude [" + latitude + "] is invalid: it should be in range [" + LATITUDE_RANGE + "]");
        }
    }

    public static void validateLongitude(@NonNull final BigDecimal longitude) {
        if (!LONGITUDE_RANGE.contains(longitude)) {
            throw new IllegalArgumentException("longitude [" + longitude + "] is invalid: it should be in range [" + LONGITUDE_RANGE + "]");
        }
    }

    @NonNull
    public BigDecimal getLongitude() {
        return this.longitude;
    }

    @NonNull
    public BigDecimal getLatitude() {
        return this.latitude;
    }

    public String toLatLongString() {
        return this.latitude + "," + this.longitude;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (getClass() != other.getClass()) {
            return false;
        }

        final GeoLocation that = (GeoLocation) other;

        return Objects.equals(this.latitude, that.latitude) &&
               Objects.equals(this.longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.latitude, this.longitude);
    }

    @Override
    public String toString() {
        return "GeoLocation{" +
               "latitude=" + this.latitude +
               ", longitude=" + this.longitude +
               '}';
    }


}
