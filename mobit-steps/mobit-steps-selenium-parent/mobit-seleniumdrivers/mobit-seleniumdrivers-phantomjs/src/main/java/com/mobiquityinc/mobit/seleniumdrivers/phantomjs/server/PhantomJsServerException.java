package com.mobiquityinc.mobit.seleniumdrivers.phantomjs.server;

public final class PhantomJsServerException extends RuntimeException {

    public PhantomJsServerException() {
    }

    public PhantomJsServerException(final String message) {
        super(message);
    }

    public PhantomJsServerException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PhantomJsServerException(final Throwable cause) {
        super(cause);
    }

    public PhantomJsServerException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
