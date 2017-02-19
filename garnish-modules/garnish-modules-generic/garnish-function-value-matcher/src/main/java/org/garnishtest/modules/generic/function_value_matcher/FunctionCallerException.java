package org.garnishtest.modules.generic.function_value_matcher;

public final class FunctionCallerException extends RuntimeException {

    public FunctionCallerException() {
    }

    public FunctionCallerException(final String message) {
        super(message);
    }

    public FunctionCallerException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public FunctionCallerException(final Throwable cause) {
        super(cause);
    }

}
