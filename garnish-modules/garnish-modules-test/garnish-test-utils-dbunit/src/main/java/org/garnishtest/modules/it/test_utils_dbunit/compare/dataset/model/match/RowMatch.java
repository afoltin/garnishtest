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
