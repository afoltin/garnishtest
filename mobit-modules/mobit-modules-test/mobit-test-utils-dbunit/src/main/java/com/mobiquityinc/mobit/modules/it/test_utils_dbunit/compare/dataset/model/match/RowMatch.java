package com.mobiquityinc.mobit.modules.it.test_utils_dbunit.compare.dataset.model.match;

import lombok.NonNull;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class RowMatch {

    @NonNull private final String tableName;
    private final int expectedRowIndex;
    private final int actualRowsIndex;

    public RowMatch(@NonNull final String tableName,
                    final int expectedRowIndex,
                    final int actualRowsIndex) {
        this.tableName = tableName;
        this.expectedRowIndex = expectedRowIndex;
        this.actualRowsIndex = actualRowsIndex;
    }

    @NonNull
    public String getTableName() {
        return this.tableName;
    }

    public int getExpectedRowIndex() {
        return this.expectedRowIndex;
    }

    public int getActualRowsIndex() {
        return this.actualRowsIndex;
    }

    @Override
    public String toString() {
        return "(" + this.tableName + ":" + this.expectedRowIndex + "-" + this.actualRowsIndex + ")";
    }

}
