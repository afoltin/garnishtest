package org.garnishtest.modules.it.test_utils_json.load;

public final class JsonLoaderException extends RuntimeException {

    public JsonLoaderException() {
    }

    public JsonLoaderException(final String message) {
        super(message);
    }

    public JsonLoaderException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public JsonLoaderException(final Throwable cause) {
        super(cause);
    }

}
