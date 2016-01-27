package com.mobiquityinc.mobit.demo.rest.web.service.geocoding;

import com.google.common.collect.ImmutableMap;
import com.mobiquityinc.mobit.demo.rest.web.service.geocoding.impl.model.GoogleGeoCodingGeometry;
import com.mobiquityinc.mobit.demo.rest.web.service.geocoding.impl.model.GoogleGeoCodingLocation;
import com.mobiquityinc.mobit.demo.rest.web.service.geocoding.impl.model.GoogleGeoCodingResult;
import com.mobiquityinc.mobit.demo.rest.web.service.geocoding.impl.model.GoogleGeoCodingResults;
import com.mobiquityinc.mobit.demo.rest.web.service.geocoding.model.GeoLocation;
import com.mobiquityinc.mobit.demo.rest.web.utils.JsonUtils;
import com.mobiquityinc.mobit.modules.generic.httpclient.SimpleHttpClient;
import com.mobiquityinc.mobit.modules.generic.httpclient.model.HttpMethod;
import com.mobiquityinc.mobit.modules.generic.httpclient.model.HttpResponse;
import com.mobiquityinc.mobit.modules.generic.uri.UriQuery;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

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

        final HttpResponse response = this.simpleHttpClient.request(HttpMethod.GET, "/maps/api/geocode/json?" + query)
                                                           .execute();

        if (response.getStatusCode() != HttpStatus.OK.value()) {
            throw new GeoCodingException(
                    "failed to geocode [" + address + "]"
                    + "; responseCode=[" + response.getStatusCode() + "]"
                    + "; responseBody=[" + response.getBodyAsString() + "]"
            );
        }


        return parseResponse(response.getBodyAsString());

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
