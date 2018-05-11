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
