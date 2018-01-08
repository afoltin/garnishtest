package org.garnishtest.demo.rest_complex.web.service;

import java.util.UUID;

public final class TokenGenerator {

    public String generateNewAuthenticationToken() {
        return UUID.randomUUID().toString();
    }

}
