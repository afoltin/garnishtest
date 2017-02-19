package com.mobiquityinc.mobit.modules.it.test_utils_dbunit.compare.dataset.model.match;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;

import javax.annotation.concurrent.Immutable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Immutable
public final class DataSetMatch {

    @NonNull
    private final ImmutableList<RowMatch> rowMatches;

    public DataSetMatch(@NonNull final List<RowMatch> rowMatches) {
        this.rowMatches = ImmutableList.copyOf(rowMatches);
    }

    public DataSetMatch() {
        this(Collections.<RowMatch>emptyList());
    }

    public DataSetMatch addRowMatch(@NonNull final String expectedTableName,
                                    final int expectedRowIndex,
                                    final int actualRowsIndex) {
        final ArrayList<RowMatch> newRowMatches = new ArrayList<>(
                this.rowMatches.size() + 1
        );

        newRowMatches.addAll(this.rowMatches);
        newRowMatches.add(
                new RowMatch(expectedTableName, expectedRowIndex, actualRowsIndex)
        );

        return new DataSetMatch(newRowMatches);

    }

    @NonNull
    public ImmutableList<RowMatch> getRowMatches() {
        return this.rowMatches;
    }

    @Override
    public String toString() {
        return this.rowMatches.toString();
    }
}
