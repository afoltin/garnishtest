package org.garnishtest.modules.it.test_utils_dbunit.compare.dataset.row_compare;

import org.garnishtest.modules.generic.function_value_matcher.FunctionValueMatcher;
import lombok.NonNull;
import org.garnishtest.modules.it.test_utils_dbunit.compare.dataset.model.match.RowMatch;
import org.garnishtest.modules.it.test_utils_dbunit.compare.dataset.placeholders.DbUnitPlaceholders;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.TypeCastException;

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

            // don't compare columns that are not in the expected file
            if (expectedValue == null) {
                continue;
            }

            final boolean newPlaceholderRecorded = placeholders.recordPlaceholderIfPossible(expectedValue, actualValue);
            if (newPlaceholderRecorded) {
                // we just recorded the value of the placeholder, so there is nothing to compare to
                continue;
            }

            if (expectedValue.startsWith(FUNCTION_PREFIX)) {
                // use function to match
                if (!rowsMatchUsingFunctions(expectedValue, actualValue)) {
                    return false;
                }
            } else {
                if (expectedValue.startsWith(ESCAPED_FUNCTION_PREFIX)) {
                    final String expectedValueWithoutEscape = expectedValue.substring(
                            ESCAPED_FUNCTION_PREFIX.length() - FUNCTION_PREFIX.length()
                    );

                    if (!rowsMatchDefault(placeholders, column, expectedValueWithoutEscape, actualValue)) {
                        return false;
                    }
                } else {
                    if (!rowsMatchDefault(placeholders, column, expectedValue, actualValue)) {
                        return false;
                    }
                }
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
                                     final String expectedValue,
                                     final String actualValue) throws TypeCastException {
        final String expectedValueWithPlaceholdersReplaced = placeholders.replacePlaceholders(expectedValue);

        final DataType columnDataType = column.getDataType();

        return columnDataType.compare(expectedValueWithPlaceholdersReplaced, actualValue) == 0;
    }

    @Nullable
    private static String nullableToString(@Nullable final Object object) {
        if (object == null) {
            return null;
        }

        return String.valueOf(object);
    }

}
