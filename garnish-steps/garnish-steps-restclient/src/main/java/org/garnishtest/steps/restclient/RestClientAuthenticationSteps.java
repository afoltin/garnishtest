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
