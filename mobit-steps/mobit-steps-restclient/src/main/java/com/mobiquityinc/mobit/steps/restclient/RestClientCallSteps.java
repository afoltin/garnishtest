package com.mobiquityinc.mobit.steps.restclient;

import com.mobiquityinc.mobit.modules.generic.httpclient.HttpRequestBuilder;
import com.mobiquityinc.mobit.modules.generic.httpclient.SimpleHttpClient;
import com.mobiquityinc.mobit.modules.generic.httpclient.model.HttpMethod;
import com.mobiquityinc.mobit.modules.generic.httpclient.model.HttpResponse;
import com.mobiquityinc.mobit.modules.generic.springutils.ClasspathUtils;
import com.mobiquityinc.mobit.modules.generic.uri.UriQuery;
import com.mobiquityinc.mobit.modules.generic.variables_resolver.impl.escape.impl.ValueEscapers;
import com.mobiquityinc.mobit.modules.it.test_utils_json.JsonUtils;
import com.mobiquityinc.mobit.steps.restclient.manager.ApiClientResponseManager;
import com.mobiquityinc.mobit.steps.vars.resource_files_vars.ResourceFilesVariables;
import com.mobiquityinc.mobit.steps.vars.scenario_attrs.ScenarioAttribute;
import com.mobiquityinc.mobit.steps.vars.scenario_user_vars.ScenarioUserVariables;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import lombok.NonNull;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Map;


public class RestClientCallSteps {

    @NonNull
    private static final ScenarioAttribute<MultipartEntityBuilder> MULTIPART_REQUEST_SCENARIO_ATTR = ScenarioAttribute.create();

    @NonNull
    private static final ScenarioAttribute<Map<String, String>> PREPARED_HEADERS = ScenarioAttribute.create();
    @NonNull
    private static final ScenarioAttribute<String> PREPARED_REQUEST_BODY = ScenarioAttribute.create();

    @NonNull
    private final ApiClientResponseManager responseManager;

    @NonNull
    private final SimpleHttpClient httpClient;

    @Autowired
    public RestClientCallSteps(@NonNull @Qualifier("mobitStepsRestClient_responseManager") final ApiClientResponseManager responseManager,
                               @NonNull @Qualifier("mobitStepsRestClient_httpClient") final SimpleHttpClient httpClient) {
        this.responseManager = responseManager;
        this.httpClient = httpClient;
    }

    @Given("^the request headers$")
    public void the_request_headers(@NonNull final Map<String, String> requestHeaders) {
        PREPARED_HEADERS.setValue(requestHeaders);
    }

    @Given("^the request form body$")
    public void the_request_form_body(@NonNull final Map<String, String> requestFormParameters) {
        final UriQuery query = UriQuery.fromSingleValuedParameters(requestFormParameters);

        PREPARED_REQUEST_BODY.setValue(
                query.toStringWithoutInitialQuestionMark()
        );
    }

    @When("^I call '(POST|PUT|PATCH)' on '(.+)' with JSON from '(.+)' and previously provided headers$")
    public void callMethodOnUrlWithJsonBodyAndHeaders(
            @NonNull final HttpMethod method,
            @NonNull final String url,
            @NonNull final String jsonReqBodyFile
    ) {
        String jsonBody = ClasspathUtils.loadFromClasspath(ResourceFilesVariables.getResourceFilesPrefix() + jsonReqBodyFile);

        jsonBody = ScenarioUserVariables.resolveInText(jsonBody, ValueEscapers.json());
        jsonBody = JsonUtils.makeValidJson(jsonBody);


        HttpRequestBuilder requestBuilder = this.httpClient.request(method, url);

        final Map<String, String> requestHeaders = PREPARED_HEADERS.getValue();
        if (requestHeaders != null) {
            for (final Map.Entry<String, String> entry : requestHeaders.entrySet()) {
                requestBuilder = requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        requestBuilder = requestBuilder.body(jsonBody, ContentType.APPLICATION_JSON);
        HttpResponse response = requestBuilder.execute();

        this.responseManager.setResponse(response);
    }

    @When("^I call '(PUT|POST|PATCH)' on '(.+)' with previously prepared request$")
    public void when_call_method_on_url_with_previously_prepared_request(
            @NonNull final HttpMethod method,
            @NonNull final String url
    ) {
        final String requestBody = PREPARED_REQUEST_BODY.getValue();
        if (requestBody == null) {
            throw new IllegalArgumentException("missing request body; please use one of the steps that set it");
        }


        HttpRequestBuilder requestBuilder = this.httpClient.request(method, url);

        final Map<String, String> requestHeaders = PREPARED_HEADERS.getValue();
        if (requestHeaders != null) {
            for (final Map.Entry<String, String> entry : requestHeaders.entrySet()) {
                requestBuilder = requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        requestBuilder = requestBuilder.body(requestBody);

        final HttpResponse response = requestBuilder.execute();

        this.responseManager.setResponse(response);
    }

    @When("^I call '(PUT|POST|PATCH)' on '(.+)' with JSON from '(.+)'$")
    public void callMethodOnUrlWithJsonBody(
            @NonNull final HttpMethod method,
            @NonNull final String url,
            @NonNull final String jsonReqBodyFile
    ) {
        String jsonBody = ClasspathUtils.loadFromClasspath(ResourceFilesVariables.getResourceFilesPrefix() + jsonReqBodyFile);

        jsonBody = ScenarioUserVariables.resolveInText(jsonBody, ValueEscapers.json());
        jsonBody = JsonUtils.makeValidJson(jsonBody);

        final HttpResponse response = this.httpClient.request(method, url)
                .body(jsonBody, ContentType.APPLICATION_JSON)
                .execute();

        this.responseManager.setResponse(response);
    }

    @When("^I call '(PUT|POST|PATCH)' on '(.+)' with JSON '(.+)'$")
    public void callMethodOnUrlWithJsonBodyFromString(
            @NonNull final HttpMethod method,
            @NonNull final String url,
            @NonNull final String jsonReqBody
    ) {

        // todo: move these 2 into support code (into the client of another class)
        String jsonBody = jsonReqBody;
        jsonBody = ScenarioUserVariables.resolveInText(jsonBody, ValueEscapers.json());
        jsonBody = JsonUtils.makeValidJson(jsonBody);


        final HttpResponse response = this.httpClient.request(method, url)
                .body(jsonBody, ContentType.APPLICATION_JSON)
                .execute();

        this.responseManager.setResponse(response);
    }

    @When("^I call '(PUT|POST|PATCH)' on '(.+)' with body '(.+)'$")
    public void callMethodOnUrlWithBodyFromString(
            @NonNull final HttpMethod method,
            @NonNull final String url,
            @NonNull final String reqBody
    ) {

        // todo: move into support code (into the client of another class)
        String body = reqBody;
        body = ScenarioUserVariables.resolveInText(body, ValueEscapers.json());

        final HttpResponse response = this.httpClient.request(method, url)
                .body(body)
                .execute();

        this.responseManager.setResponse(response);
    }

    @Given("^a multipart request field '([^']+)' with value '([^']*)' and content type '([^']+)'$")
    public void a_multipart_request_field_name_value_contentType(@NonNull final String fieldName,
                                                                 @NonNull final String fieldValue,
                                                                 @NonNull final String contentType) throws UnsupportedEncodingException {
        MultipartEntityBuilder multipart = MULTIPART_REQUEST_SCENARIO_ATTR.getValue();
        if (multipart == null) {
            multipart = MultipartEntityBuilder.create();
            MULTIPART_REQUEST_SCENARIO_ATTR.setValue(multipart);
        }

        multipart.addPart(fieldName, new StringBody(fieldValue, ContentType.create(contentType)));
    }

    @Given("^a multipart request field '([^']+)' with value from file '([^']+)' and content type '([^']+)'$")
    public void a_multipart_request_field_name_fileValue_contentType(@NonNull final String fieldName,
                                                                     @NonNull final String fieldValueFilePath,
                                                                     @NonNull final String contentType) throws UnsupportedEncodingException {
        a_multipart_request_field_name_value_contentType(
                fieldName,
                ClasspathUtils.loadFromClasspath(ResourceFilesVariables.getResourceFilesPrefix() + fieldValueFilePath),
                contentType
        );
    }

    @Given("^a multipart request field '([^']+)' with value from JSON file '([^']+)'$")
    public void a_multipart_request_field_name_jsonFileValue_contentType(@NonNull final String fieldName,
                                                                         @NonNull final String fieldValueJsonFilePath) throws UnsupportedEncodingException {
        String jsonBody = ClasspathUtils.loadFromClasspath(ResourceFilesVariables.getResourceFilesPrefix() + fieldValueJsonFilePath);

        jsonBody = ScenarioUserVariables.resolveInText(jsonBody, ValueEscapers.json());
        jsonBody = JsonUtils.makeValidJson(jsonBody);

        a_multipart_request_field_name_value_contentType(fieldName, jsonBody, ContentType.APPLICATION_JSON.getMimeType());
    }

    @When("^I call multipart '(GET|PUT|POST|DELETE|HEAD|OPTIONS)' on '(.+)'$")
    public void callMultipartMethodOnUrlWithJsonBody(
            @NonNull final HttpMethod method,
            @NonNull final String url
    ) {
        final MultipartEntityBuilder multipartBody = MULTIPART_REQUEST_SCENARIO_ATTR.getRequiredValue();
        final HttpResponse response = this.httpClient.request(method, url)
                .body(multipartBody)
                .execute();

        this.responseManager.setResponse(response);
    }

    @When("^I call '(GET|DELETE|HEAD|OPTIONS|TRACE|PUT|POST|PATCH)' on '(.+)' without body$")
    public void callMethodOnUrlWithoutBody(
            @NonNull final HttpMethod method,
            @NonNull final String url
    ) throws URISyntaxException {
        final HttpResponse response = performJsonRequestWithoutBody(method, url);

        this.responseManager.setResponse(response);
    }

    // todo: move this method to a separate support class
    public HttpResponse performJsonRequestWithoutBody(@NonNull final HttpMethod method, @NonNull final String url) {
        return this.httpClient.request(method, url)
                .setHeader("Content-Type", "application/json")
                .execute();
    }

}
