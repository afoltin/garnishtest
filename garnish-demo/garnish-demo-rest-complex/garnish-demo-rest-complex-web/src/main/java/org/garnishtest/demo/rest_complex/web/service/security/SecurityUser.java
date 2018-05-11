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

package org.garnishtest.demo.rest_complex.web.service.security;

import org.garnishtest.demo.rest_complex.web.dao.model.User;
import lombok.NonNull;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class SecurityUser {

    private final long id;
    @NonNull private final String name;
    @NonNull private final String username;
    @NonNull private final SecurityUserAddress address;

    public SecurityUser(final long id,
                        @NonNull final String name,
                        @NonNull final String username,
                        @NonNull final SecurityUserAddress address) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.address  = address;
    }

    public static SecurityUser fromDbUser(@NonNull final User user) {
        return new SecurityUser(
                user.getId(),
                user.getName(),
                user.getUsername(),
                SecurityUserAddress.fromDbAddress(user.getAddress())
        );
    }


    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getUsername() {
        return this.username;
    }

    public SecurityUserAddress getAddress() {
        return address;
    }
}
