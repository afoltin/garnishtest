package com.mobiquityinc.mobit.modules.generic.jaxb;

public final class JaxbParserException extends RuntimeException {

    public JaxbParserException() {
    }

    public JaxbParserException(final String message) {
        super(message);
    }

    public JaxbParserException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public JaxbParserException(final Throwable cause) {
        super(cause);
    }

    public JaxbParserException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
