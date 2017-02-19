package org.garnishtest.demo.rest.web.service.security;

import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public final class SecurityAuthentication implements Authentication {

    @NonNull private final String token;

    private boolean authenticated = false;
    private SecurityUser securityUser;

    public SecurityAuthentication(@NonNull final String token) {
        this.token = token;
    }

    @NonNull
    public String getToken() {
        return this.token;
    }

    public SecurityUser getSecurityUser() {
        return this.securityUser;
    }

    public void setSecurityUser(final SecurityUser securityUser) {
        this.securityUser = securityUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(final boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return null;
    }

}
