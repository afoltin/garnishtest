package org.garnishtest.steps.restclient;

import org.garnishtest.steps.restclient.auth.preparer.AuthenticationHttpRequestPreparer;
import cucumber.api.java.en.Given;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public final class RestClientAuthenticationSteps {

    @NonNull private final AuthenticationHttpRequestPreparer authenticationHttpRequestPreparer;

    @Autowired
    public RestClientAuthenticationSteps(@NonNull @Qualifier("garnishStepsRestClient_authenticationHttpRequestPreparer")
                                         final AuthenticationHttpRequestPreparer authenticationHttpRequestPreparer) {
        this.authenticationHttpRequestPreparer = authenticationHttpRequestPreparer;
    }

    @Given("^I am not logged-in$")
    public void given_I_am_not_logged_in() {
        this.authenticationHttpRequestPreparer.logout();
    }

    @Given("^I am logged in as '(.*)'$")
    public void given_I_am_logged_in_to_Shift_as_username(@NonNull final String username) {
        this.authenticationHttpRequestPreparer.login(username);
    }

}
