package org.garnishtest.demo.rest.web.service.security;

import org.garnishtest.demo.rest.web.dao.model.User;
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
