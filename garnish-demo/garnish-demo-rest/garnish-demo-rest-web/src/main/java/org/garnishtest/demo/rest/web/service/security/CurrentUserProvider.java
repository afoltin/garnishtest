package org.garnishtest.demo.rest.web.service.security;

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
