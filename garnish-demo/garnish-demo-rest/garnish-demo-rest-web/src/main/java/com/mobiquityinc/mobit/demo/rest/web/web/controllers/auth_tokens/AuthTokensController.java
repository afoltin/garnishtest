package com.mobiquityinc.mobit.demo.rest.web.web.controllers.auth_tokens;

import com.mobiquityinc.mobit.demo.rest.web.dao.model.AuthToken;
import com.mobiquityinc.mobit.demo.rest.web.service.AuthTokensService;
import com.mobiquityinc.mobit.demo.rest.web.web.controllers.auth_tokens.model.CreateTokenRequest;
import com.mobiquityinc.mobit.demo.rest.web.web.controllers.auth_tokens.model.CreateTokenResponse;
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
