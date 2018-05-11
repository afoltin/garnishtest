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

package org.garnishtest.demo.rest_complex.web.web.controllers.users.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class GetUserResponse {

    private final long id;
    private final String name;
    private final String username;
    private final GetUserAddress address;

    @JsonCreator
    public GetUserResponse(@JsonProperty("id") final long id,
                           @JsonProperty("name") final String name,
                           @JsonProperty("username") final String username,
                           @JsonProperty("address") final GetUserAddress address) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.address = address;
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

    public GetUserAddress getAddress() {
        return address;
    }
}
