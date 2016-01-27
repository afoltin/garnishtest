package com.mobiquityinc.mobit.demo.rest.web.dao.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;

public final class Address {

    private Long id;
    private Long userId;
    private String textualAddress;
    private BigDecimal latitude;
    private BigDecimal longitude;

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public String getTextualAddress() {
        return this.textualAddress;
    }

    public void setTextualAddress(final String textualAddress) {
        this.textualAddress = textualAddress;
    }

    public BigDecimal getLatitude() {
        return this.latitude;
    }

    public void setLatitude(final BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return this.longitude;
    }

    public void setLongitude(final BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }

}
