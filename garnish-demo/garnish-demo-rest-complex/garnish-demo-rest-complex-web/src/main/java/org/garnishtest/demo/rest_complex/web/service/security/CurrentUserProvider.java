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

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.Nullable;

public final class CurrentUserProvider {

    @Nullable
    public SecurityUser getCurrentUser() {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext == null) {
            return null;
        }

        final Authentication authentication = securityContext.getAuthentication();
        if (authentication == null) {
            return null;
        }
        if (!(authentication instanceof SecurityAuthentication)) {
            return null;
        }

        final SecurityAuthentication securityAuthentication = (SecurityAuthentication) authentication;

        return securityAuthentication.getSecurityUser();
    }

}
