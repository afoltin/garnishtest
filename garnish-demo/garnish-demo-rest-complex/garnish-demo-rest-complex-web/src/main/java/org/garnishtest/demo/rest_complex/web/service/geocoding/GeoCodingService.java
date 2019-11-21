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

package org.garnishtest.demo.rest_complex.web.service.geocoding;

import com.google.common.collect.ImmutableMap;
import org.garnishtest.demo.rest_complex.web.service.geocoding.impl.model.GoogleGeoCodingGeometry;
import org.garnishtest.demo.rest_complex.web.service.geocoding.impl.model.GoogleGeoCodingLocation;
import org.garnishtest.demo.rest_complex.web.service.geocoding.impl.model.GoogleGeoCodingResult;
import org.garnishtest.demo.rest_complex.web.service.geocoding.impl.model.GoogleGeoCodingResults;
import org.garnishtest.demo.rest_complex.web.service.geocoding.model.GeoLocation;
import org.garnishtest.demo.rest_complex.web.utils.JsonUtils;
import org.garnishtest.modules.generic.httpclient.SimpleHttpClient;
import org.garnishtest.modules.generic.httpclient.model.HttpMethod;
import org.garnishtest.modules.generic.uri.UriQuery;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

public final class GeoCodingService {

    @NonNull private final SimpleHttpClient simpleHttpClient;

    public GeoCodingService(@NonNull final SimpleHttpClient simpleHttpClient) {
        this.simpleHttpClient = simpleHttpClient;
    }

    public GeoLocation geoCode(@NonNull final String address) {
        final UriQuery query = new UriQuery(
                ImmutableMap.<String, List<String>>builder()
                        .put("sensor", Collections.singletonList("false"))
                        .put("address", Collections.singletonList(address))
                        .build()
        );

        final HttpResponse<String>
            response = this.simpleHttpClient.request(HttpMethod.GET, "/maps/api/geocode/json?" + query)
                                                           .execute();

        if (response.statusCode() != HttpStatus.OK.value()) {
            throw new GeoCodingException(
                    "failed to geocode [" + address + "]"
                    + "; responseCode=[" + response.statusCode() + "]"
                    + "; responseBody=[" + response.body() + "]"
            );
        }


        return parseResponse(response.body());

    }

    @NonNull
    private GeoLocation parseResponse(@NonNull final String responseAsString) {
        final GoogleGeoCodingResults geoCodingResultsObject = JsonUtils.parseJson(
                responseAsString,
                GoogleGeoCodingResults.class
        );
        if (geoCodingResultsObject == null) {
            throw new GeoCodingException("null response");
        }

        final List<GoogleGeoCodingResult> geoCodingResults = geoCodingResultsObject.getResults();
        if (geoCodingResults == null) {
            throw new GeoCodingException("null results");
        }

        if (geoCodingResults.isEmpty()) {
            throw new GeoCodingException("empty results");
        }

        final GoogleGeoCodingResult firstResult = geoCodingResults.get(0);
        if (firstResult == null) {
            throw new GeoCodingException("unexpected response: ($.[0] == null)");
        }

        final GoogleGeoCodingGeometry geometry = firstResult.getGeometry();
        if (geometry == null) {
            throw new GeoCodingException("unexpected response: ($.[0].geometry == null)");
        }

        final GoogleGeoCodingLocation location = geometry.getLocation();

        return location.getGeoLocation();
    }

}
