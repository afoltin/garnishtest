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

package org.garnishtest.demo.rest_complex.web.web.controllers.auth_tokens;

import org.garnishtest.demo.rest_complex.web.dao.model.AuthToken;
import org.garnishtest.demo.rest_complex.web.service.AuthTokensService;
import org.garnishtest.demo.rest_complex.web.web.controllers.auth_tokens.model.CreateTokenRequest;
import org.garnishtest.demo.rest_complex.web.web.controllers.auth_tokens.model.CreateTokenResponse;
import lombok.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public final class AuthTokensController {

    @NonNull private final AuthTokensService authTokensService;

    public AuthTokensController(@NonNull final AuthTokensService authTokensService) {
        this.authTokensService = authTokensService;
    }

    @RequestMapping(value = "/users/{userName}/tokens", method = RequestMethod.POST)
    @ResponseBody
    public CreateTokenResponse createToken(@NonNull @PathVariable("userName") final String username,
                                           @NonNull @RequestBody final CreateTokenRequest createTokenRequest) {
        final AuthToken authToken = this.authTokensService.createToken(username, createTokenRequest.getPassword());

        return new CreateTokenResponse(
                authToken.getToken()
        );
    }

}
