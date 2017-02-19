package org.garnishtest.steps.restclient;

import com.jayway.jsonpath.JsonPath;
import org.garnishtest.modules.generic.variables_resolver.impl.escape.impl.ValueEscapers;
import org.garnishtest.modules.it.test_utils_json.compare.SmartJsonAssert;
import org.garnishtest.steps.restclient.manager.ApiClientResponseManager;
import org.garnishtest.modules.generic.springutils.ClasspathUtils;
import org.garnishtest.steps.vars.resource_files_vars.ResourceFilesVariables;
import org.garnishtest.steps.vars.scenario_user_vars.ScenarioUserVariables;
import cucumber.api.java.en.Then;
import lombok.NonNull;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public final class RestClientAssertionSteps {

    @NonNull private final ApiClientResponseManager apiClientResponseManager;
    @NonNull private final SmartJsonAssert smartJsonAssert;

    @Autowired
    public RestClientAssertionSteps(@NonNull @Qualifier("garnishStepsRestClient_responseManager") final ApiClientResponseManager apiClientResponseManager,
                                    @NonNull @Qualifier("garnishStepsRestClient_smartJsonAssert") final SmartJsonAssert smartJsonAssert) {
        this.apiClientResponseManager = apiClientResponseManager;
        this.smartJsonAssert = smartJsonAssert;
    }

    @Then("^the response code should be (\\d{1,3})$")
    public void the_response_code_should_be(final int expectedResponseCode) {
        final int actualStatusCode = this.apiClientResponseManager.getResponse().getStatusCode();

        // todo: nicer error message (include the code name as string)
        assertThat(actualStatusCode).isEqualTo(expectedResponseCode);
    }
    @Then("^the response code should not be (\\d{1,3})$")
    public void the_response_code_should_not_be(final int expectedResponseCode) {
        final int actualStatusCode = this.apiClientResponseManager.getResponse().getStatusCode();

        assertThat(actualStatusCode).isNotEqualTo(expectedResponseCode);
    }

    @Then("^the response header '(.+)' should match '(.*)'$")
    public void the_response_header_should_match(@NonNull final String headerName,
                                                 @NonNull final String expectedHeaderValueRegexp) {
        final String headerValue = this.apiClientResponseManager.getResponse()
                                                                .getFirstHeaderValue(headerName)
                                                                .orNull();

        assertThat(headerValue).matches(expectedHeaderValueRegexp);
    }

    @Then("^the response header '(.+)' should be equal to '(.*)'$")
    public void the_response_header_should_be_equal_to(@NonNull final String headerName,
                                                       @NonNull final String expectedHeaderValue) {
        final String headerValue = this.apiClientResponseManager.getResponse()
                                                                .getFirstHeaderValue(headerName)
                                                                .orNull();

        assertThat(headerValue).isEqualTo(expectedHeaderValue);
    }

    @Then("the response body should be like in the file '(.+)'")
    public void the_response_body_should_be_like_in_file(@NonNull final String expectedFile) throws JSONException {
        final String expectedFileContent = ClasspathUtils.loadFromClasspath(ResourceFilesVariables.getResourceFilesPrefix() + expectedFile);

        assertTextEquals(expectedFileContent, this.apiClientResponseManager.getBody());
    }

    @Then("the response body should be like in the JSON file '(.+)'")
    public void the_response_body_should_be_like_in_json_file(@NonNull final String expectedJsonFile) throws JSONException {
        final String expectedFileContent = ClasspathUtils.loadFromClasspath(ResourceFilesVariables.getResourceFilesPrefix() + expectedJsonFile);

        assertJsonEquals(expectedFileContent, this.apiClientResponseManager.getBody());
    }

    @Then("the JsonPath '(.+)' applied to the response body should have the value '(.+)'")
    public void the_jsonPath_expression_should_have_value(@NonNull final String jsonPath,
                                                          @NonNull final String expectedValue) throws JSONException {
        final String value = String.valueOf(
                JsonPath.read(this.apiClientResponseManager.getBody(), jsonPath)
        );

        assertJsonEquals(expectedValue, value);
    }

    @Then("the JsonPath '(.+)' applied to the response body should match '(.+)'")
    public void the_jsonPath_expression_should_match(@NonNull final String jsonPath,
                                                     @NonNull final String expectedRegex) throws JSONException {
        final String value = String.valueOf(
                JsonPath.read(this.apiClientResponseManager.getBody(), jsonPath)
        );

        final Pattern expectedPattern = Pattern.compile(expectedRegex);
        final Matcher expectedMatcher = expectedPattern.matcher(value);

        if (!expectedMatcher.matches()) {
            throw new AssertionError("value of jsonPath [" + jsonPath + "], which is [" + value + "], doesn't match regex [" + expectedRegex + "]");
        }
    }

    // todo: needs to be moved to a separate support class
    public void assertTextEquals(@NonNull final String expectedText,
                                  @NonNull final String actualText) throws JSONException {
        final String resolvedExpectedText = ScenarioUserVariables.resolveInText(expectedText, ValueEscapers.json());

        assertThat(actualText).isEqualTo(resolvedExpectedText);
    }

    // todo: needs to be moved to a separate support class
    public void assertJsonEquals(@NonNull final String expectedJson,
                                  @NonNull final String actualJson) throws JSONException {
        final String resolvedExpectedJson = ScenarioUserVariables.resolveInText(expectedJson, ValueEscapers.json());

        this.smartJsonAssert.assertEquals(resolvedExpectedJson, actualJson);
    }

}
