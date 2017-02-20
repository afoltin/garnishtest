package org.garnishtest.demo.rest_complex.web.dao.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

public final class AuthToken {

    private Long id;
    private String token;
    private Date dateCreated;

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public Date getDateCreated() {
        return this.dateCreated;
    }

    public void setDateCreated(final Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }
}
