package org.garnishtest.demo.rest_complex.web.utils;

public final class JsonUtilsException extends RuntimeException {

    public JsonUtilsException() {
    }

    public JsonUtilsException(final String message) {
        super(message);
    }

    public JsonUtilsException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public JsonUtilsException(final Throwable cause) {
        super(cause);
    }

    public JsonUtilsException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
