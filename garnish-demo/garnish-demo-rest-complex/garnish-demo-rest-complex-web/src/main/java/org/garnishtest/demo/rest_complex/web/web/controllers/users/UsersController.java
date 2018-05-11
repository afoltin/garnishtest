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

package org.garnishtest.demo.rest_complex.web.web.controllers.users;

import org.garnishtest.demo.rest_complex.web.service.UsersService;
import org.garnishtest.demo.rest_complex.web.service.security.SecurityUser;
import org.garnishtest.demo.rest_complex.web.service.security.SecurityUserAddress;
import org.garnishtest.demo.rest_complex.web.web.controllers.users.model.CreateUserRequest;
import org.garnishtest.demo.rest_complex.web.web.controllers.users.model.CreateUserResponse;
import org.garnishtest.demo.rest_complex.web.web.controllers.users.model.GetUserAddress;
import org.garnishtest.demo.rest_complex.web.web.controllers.users.model.GetUserResponse;
import lombok.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/users")
public final class UsersController {

    @NonNull private final UsersService usersService;

    public UsersController(@NonNull final UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public CreateUserResponse createUser(@NonNull @RequestBody final CreateUserRequest request) {
        final long userId = this.usersService.createUser(request);

        return new CreateUserResponse(userId);
    }

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    @ResponseBody
    public GetUserResponse getCurrentUser() {
        final SecurityUser currentUser = this.usersService.getCurrentUser();

        return new GetUserResponse(
                currentUser.getId(),
                currentUser.getName(),
                currentUser.getUsername(),
                convertAddress(currentUser.getAddress())
        );
    }

    @NonNull
    private GetUserAddress convertAddress(@NonNull final SecurityUserAddress address) {
        return new GetUserAddress(
                address.getTextualAddress(),
                address.getLatitude(),
                address.getLongitude()
        );
    }

}
