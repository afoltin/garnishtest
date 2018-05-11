/*
 * Copyright 2016-2018, Garnish.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.garnishtest.modules.it.test_utils_dbunit.compare.dataset.model.match;

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
