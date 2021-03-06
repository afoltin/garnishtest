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

package org.garnishtest.modules.it.test_utils_dbunit.compare.dataset.row_compare;

import lombok.NonNull;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.TypeCastException;
import org.garnishtest.modules.generic.function_value_matcher.FunctionValueMatcher;
import org.garnishtest.modules.it.test_utils_dbunit.compare.dataset.model.match.RowMatch;
import org.garnishtest.modules.it.test_utils_dbunit.compare.dataset.placeholders.DbUnitPlaceholders;

import javax.annotation.Nullable;

public final class DbUnitRowComparer {

    private static final String FUNCTION_PREFIX = "@";
    private static final java.lang.String ESCAPED_FUNCTION_PREFIX = "\\" + FUNCTION_PREFIX;

    @NonNull
    private final FunctionValueMatcher functionValueMatcher;

    public DbUnitRowComparer(@NonNull final FunctionValueMatcher functionValueMatcher) {
        this.functionValueMatcher = functionValueMatcher;
    }

    public boolean rowsMatchIgnoringPlaceholders(@NonNull final RowMatch rowMatch,
                                                 @NonNull final IDataSet expectedDataSet,
                                                 @NonNull final IDataSet actualDataSet) {
        final DbUnitPlaceholders placeholders = new DbUnitPlaceholders();

        return rowsMatch(rowMatch, expectedDataSet, actualDataSet, placeholders);
    }

    /**
     * Checks if the data pointed by {@code rowMatch} actually matches in the given datasets.
     * <b>IMPORTANT:</b> the {@code placeholders} may be modified in the process (because the scope of placeholders is
     * at the dataset level, not at the row level)
     */
    public boolean rowsMatch(@NonNull final RowMatch rowMatch,
                             @NonNull final IDataSet expectedDataSet,
                             @NonNull final IDataSet actualDataSet,
                             @NonNull final DbUnitPlaceholders placeholders) {
        // todo: better algorithm that doesn't change the placeholders?

        try {
            return tryToCheckIfRowsMatch(rowMatch, expectedDataSet, actualDataSet, placeholders);
        } catch (final Exception e) {
            throw new DbUnitRowComparerException("failed to compare rowMatch [" + rowMatch + "]", e);
        }

    }

    private boolean tryToCheckIfRowsMatch(@NonNull final RowMatch rowMatch,
                                          @NonNull final IDataSet expectedDataSet,
                                          @NonNull final IDataSet actualDataSet,
                                          @NonNull final DbUnitPlaceholders placeholders) throws DataSetException {
        final String tableName = rowMatch.getTableName();
        final int expectedRowIndex = rowMatch.getExpectedRowIndex();
        final int actualRowsIndex = rowMatch.getActualRowsIndex();

        final ITable expectedTable = expectedDataSet.getTable(tableName);
        final ITable actualTable = actualDataSet.getTable(tableName);

        final ITableMetaData tableMetaData = expectedTable.getTableMetaData();
        final Column[] columns = tableMetaData.getColumns();

        for (final Column column : columns) {
            final String columnName = column.getColumnName();

            final String expectedValue = nullableToString(
                    expectedTable.getValue(expectedRowIndex, columnName)
            );
            final String actualValue = nullableToString(
                    actualTable.getValue(actualRowsIndex, columnName)
            );

            if (expectedValue == null /* don't compare columns that are not in the expected file */
                || placeholders.recordPlaceholderIfPossible(expectedValue,
                actualValue) /* we just recorded the value of the placeholder, so there is nothing to compare to */) {
                continue;
            }

            if (!expectedValue.startsWith(FUNCTION_PREFIX)) {
                final String expectedValueWithoutEscape;
                if (expectedValue.startsWith(ESCAPED_FUNCTION_PREFIX)) {
                    expectedValueWithoutEscape = expectedValue
                        .substring(ESCAPED_FUNCTION_PREFIX.length() - FUNCTION_PREFIX.length());
                } else {
                    expectedValueWithoutEscape = expectedValue;
                }
                if (!rowsMatchDefault(placeholders, column, expectedValueWithoutEscape,
                    actualValue)) {
                    return false;
                }
            } else if (!rowsMatchUsingFunctions(expectedValue,
                actualValue) /* use function to match */) {
                return false;
            }
        }

        return true;
    }

    private boolean rowsMatchUsingFunctions(final String expectedValue, final String actualValue) {
        final String expression = expectedValue.substring(FUNCTION_PREFIX.length());

        return this.functionValueMatcher.matches(actualValue, expression);
    }

    /** Try to match exact values. */
    private boolean rowsMatchDefault(@NonNull final DbUnitPlaceholders placeholders,
                                     @NonNull final Column column,
                                     final String expectedValue, final String actualValue) throws TypeCastException {
        final String expectedValueWithPlaceholdersReplaced = placeholders.replacePlaceholders(expectedValue);

        final DataType columnDataType = column.getDataType();

        return columnDataType.compare(expectedValueWithPlaceholdersReplaced, actualValue) == 0;
    }

    @Nullable private static String nullableToString(@Nullable final Object object) {
        if (object == null) {
            return null;
        }

        return String.valueOf(object);
    }

}
