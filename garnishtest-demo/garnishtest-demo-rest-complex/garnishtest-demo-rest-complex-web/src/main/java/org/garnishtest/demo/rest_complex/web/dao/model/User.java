package org.garnishtest.demo.rest_complex.web.dao.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public final class User {

    private Long id;
    private String name;
    private String username;
    private String password;
    private Address address;

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(final Address address) {
        this.address = address;

        if (this.address != null) {
            this.address.setUserId(this.id);
        }
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }

}
