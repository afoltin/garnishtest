package com.mobiquityinc.mobit.modules.it.test_utils_dbunit.compare.dataset.model.match;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import lombok.NonNull;

import javax.annotation.concurrent.Immutable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class helps generate all possible combinations of matches
 * given a mapping (expectedRowIndex -> list of actualRowsIndexes).
 * It does this by generating all permutations of (expectedRowIndex, actualRowsIndex) pairs.
 * <br />
 * Check the source of the test class to see exactly how it works.
 */
@Immutable
public final class DataSetMatches {

    @NonNull
    private final ImmutableList<DataSetMatch> matches;

    public DataSetMatches() {
        this(
                Collections.singletonList(
                        new DataSetMatch()
                )
        );
    }

    public DataSetMatches(@NonNull final List<DataSetMatch> matches) {
        this.matches = ImmutableList.copyOf(matches);
    }

    @NonNull
    public ImmutableList<DataSetMatch> getMatches() {
        return this.matches;
    }

    public DataSetMatches addRowMatch(@NonNull final String expectedTableName,
                                      final int expectedRowIndex,
                                      @NonNull final List<Integer> actualRowsIndexes) {
        if (actualRowsIndexes.isEmpty()) {
            throw new IllegalArgumentException("actualRowsIndexes must not be empty");
        }

        final List<DataSetMatch> newPossibleMatches = new ArrayList<>();

        for (final DataSetMatch possibleMatch : this.matches) {
            for (final Integer actualRowsIndex : actualRowsIndexes) {
                final DataSetMatch newDataSetMatch = possibleMatch.addRowMatch(
                        expectedTableName,
                        expectedRowIndex,
                        actualRowsIndex
                );

                newPossibleMatches.add(newDataSetMatch);
            }
        }

        return new DataSetMatches(newPossibleMatches);
    }

    @Override
    public String toString() {
        return Joiner.on("\n").join(this.matches);
    }

}
