package org.garnishtest.demo.rest_complex.web.service.geocoding;

public final class GeoCodingException extends RuntimeException {

    public GeoCodingException() {
    }

    public GeoCodingException(final String message) {
        super(message);
    }

    public GeoCodingException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public GeoCodingException(final Throwable cause) {
        super(cause);
    }

    public GeoCodingException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
