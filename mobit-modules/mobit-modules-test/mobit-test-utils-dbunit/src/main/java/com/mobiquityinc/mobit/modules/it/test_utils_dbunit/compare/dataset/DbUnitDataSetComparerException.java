package com.mobiquityinc.mobit.modules.it.test_utils_dbunit.compare.dataset;

public final class DbUnitDataSetComparerException extends RuntimeException {

    public DbUnitDataSetComparerException() {
    }

    public DbUnitDataSetComparerException(final String message) {
        super(message);
    }

    public DbUnitDataSetComparerException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DbUnitDataSetComparerException(final Throwable cause) {
        super(cause);
    }
}
