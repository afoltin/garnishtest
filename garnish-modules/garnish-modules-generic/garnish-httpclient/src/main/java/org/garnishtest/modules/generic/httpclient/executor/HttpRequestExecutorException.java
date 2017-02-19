package org.garnishtest.modules.generic.httpclient.executor;

public final class HttpRequestExecutorException extends RuntimeException {

    public HttpRequestExecutorException() {
    }

    public HttpRequestExecutorException(final String message) {
        super(message);
    }

    public HttpRequestExecutorException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public HttpRequestExecutorException(final Throwable cause) {
        super(cause);
    }
}
