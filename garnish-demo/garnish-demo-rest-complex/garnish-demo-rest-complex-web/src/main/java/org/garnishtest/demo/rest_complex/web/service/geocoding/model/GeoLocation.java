/*
 * Copyright 2016-2018, Garnish.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.garnishtest.demo.rest_complex.web.service.geocoding.model;

import com.google.common.collect.Range;
import lombok.NonNull;

import javax.annotation.concurrent.Immutable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Immutable
public final class GeoLocation {

    private static final int SCALE = 10;

    private static final Range<BigDecimal> LATITUDE_RANGE = Range.closed(
            new BigDecimal("-90").setScale(SCALE, RoundingMode.UNNECESSARY),
            new BigDecimal("90").setScale(SCALE, RoundingMode.UNNECESSARY)
    );
    private static final Range<BigDecimal> LONGITUDE_RANGE = Range.closed(
            new BigDecimal("-180.0").setScale(SCALE, RoundingMode.UNNECESSARY),
            new BigDecimal("180.0").setScale(SCALE, RoundingMode.UNNECESSARY)
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
