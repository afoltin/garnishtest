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

package org.garnishtest.demo.rest_complex.web.service;

import org.garnishtest.demo.rest_complex.web.dao.mappers.AddressesMapper;
import org.garnishtest.demo.rest_complex.web.dao.mappers.UsersMapper;
import org.garnishtest.demo.rest_complex.web.dao.model.Address;
import org.garnishtest.demo.rest_complex.web.dao.model.User;
import org.garnishtest.demo.rest_complex.web.service.geocoding.GeoCodingService;
import org.garnishtest.demo.rest_complex.web.service.geocoding.model.GeoLocation;
import org.garnishtest.demo.rest_complex.web.service.security.CurrentUserProvider;
import org.garnishtest.demo.rest_complex.web.service.security.SecurityUser;
import org.garnishtest.demo.rest_complex.web.web.controllers.users.model.CreateUserRequest;
import lombok.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Nullable;

public final class UsersService {

    @NonNull private final CurrentUserProvider currentUserProvider;
    @NonNull private final UsersMapper usersMapper;
    @NonNull private final AddressesMapper addressesMapper;
    @NonNull private final PasswordEncoder passwordEncoder;
    @NonNull private final GeoCodingService geoCodingService;

    public UsersService(@NonNull final CurrentUserProvider currentUserProvider,
                        @NonNull final UsersMapper usersMapper,
                        @NonNull final AddressesMapper addressesMapper,
                        @NonNull final PasswordEncoder passwordEncoder,
                        @NonNull final GeoCodingService geoCodingService) {
        this.currentUserProvider = currentUserProvider;
        this.usersMapper = usersMapper;
        this.addressesMapper = addressesMapper;
        this.passwordEncoder = passwordEncoder;
        this.geoCodingService = geoCodingService;
    }

    public long createUser(@NonNull final CreateUserRequest request) {
        final long userId = insertUser(request);
        insertAddress(request, userId);

        return userId;
    }

    private long insertUser(@NonNull final CreateUserRequest request) {
        final User user = new User();

        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setPassword(
                this.passwordEncoder.encode(
                        request.getPassword()
                )

        );

        this.usersMapper.insertUser(user);

        return user.getId();
    }

    private long insertAddress(@NonNull final CreateUserRequest request,
                               final long userId) {
        final Address address = new Address();

        address.setUserId(userId);
        address.setTextualAddress(request.getAddress());

        final GeoLocation geoLocation = this.geoCodingService.geoCode(request.getAddress());

        address.setLatitude(geoLocation.getLatitude());
        address.setLongitude(geoLocation.getLongitude());

        return this.addressesMapper.insertAddress(address);
    }

    @Nullable
    public Long getUserIdByUsernameAndPassword(final String username,
                                               final String password) {
        final User user = this.usersMapper.getUserByUsername(username);
        if (user == null) {
            return null;
        }

        final String encodedPassword = user.getPassword();
        if (!this.passwordEncoder.matches(password, encodedPassword)) {
            return null;
        }

        return user.getId();
    }


    public SecurityUser getCurrentUser() {
        return this.currentUserProvider.getCurrentUser();
    }
}
