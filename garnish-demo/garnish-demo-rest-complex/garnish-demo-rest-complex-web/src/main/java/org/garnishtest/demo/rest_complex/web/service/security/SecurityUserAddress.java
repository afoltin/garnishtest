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

package org.garnishtest.demo.rest_complex.web.service.security;

import org.garnishtest.demo.rest_complex.web.dao.model.Address;
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
