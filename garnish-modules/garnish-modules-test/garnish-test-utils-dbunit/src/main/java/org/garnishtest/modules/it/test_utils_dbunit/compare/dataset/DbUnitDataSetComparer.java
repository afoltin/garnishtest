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

package org.garnishtest.modules.it.test_utils_dbunit.compare.dataset;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import lombok.NonNull;
import org.garnishtest.modules.it.test_utils_dbunit.compare.dataset.model.match.DataSetMatch;
import org.garnishtest.modules.it.test_utils_dbunit.compare.dataset.model.match.DataSetMatches;
import org.garnishtest.modules.it.test_utils_dbunit.compare.dataset.model.match.RowMatch;
import org.garnishtest.modules.it.test_utils_dbunit.compare.dataset.placeholders.DbUnitPlaceholders;
import org.garnishtest.modules.it.test_utils_dbunit.compare.dataset.row_compare.DbUnitRowComparer;
import org.apache.commons.lang3.StringEscapeUtils;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.xml.FlatXmlWriter;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class DbUnitDataSetComparer {

    @NonNull private final DbUnitRowComparer rowComparer;

    public DbUnitDataSetComparer(@NonNull final DbUnitRowComparer rowComparer) {
        this.rowComparer = rowComparer;
    }

    public void compareDataSets(@NonNull final IDataSet expectedDataSet,
                                @NonNull final IDataSet actualDataSet) throws DbUnitDataSetComparerException {
        try {
            tryToCompareDataSets(expectedDataSet, actualDataSet);
        } catch (final DataSetException e) {
            throw new DbUnitDataSetComparerException("failed to compare data sets", e);
        }
    }

    private void tryToCompareDataSets(@NonNull final IDataSet expectedDataSet,
                                      @NonNull final IDataSet actualDataSet) throws DataSetException {
        final List<String> expectedTableNames = Arrays.asList(expectedDataSet.getTableNames());

        final DataSetMatches dataSetMatches = generatePossibleMatches(
                expectedDataSet,
                actualDataSet,
                expectedTableNames
        );

        if (!hasAtLeastOneValidMatch(expectedDataSet, actualDataSet, dataSetMatches)) {
            throw new DbUnitDataSetComparerException("the two datasets do not match");
        }
    }

    private DataSetMatches generatePossibleMatches(@NonNull final IDataSet expectedDataSet,
                                                   @NonNull final IDataSet actualDataSet,
                                                   @NonNull final List<String> expectedTableNames)
            throws DataSetException {
        DataSetMatches dataSetMatches = new DataSetMatches();

        // for each expected table
        for (final String expectedTableName : expectedTableNames) {
            final ITable expectedTable = expectedDataSet.getTable(expectedTableName);

            // for each expected row
            for (int expectedRowIndex = 0; expectedRowIndex < expectedTable.getRowCount(); expectedRowIndex++) {
                final List<Integer> matchingActualRowsIndexes = getMatchingActualRowsIndexes(
                        expectedDataSet,
                        actualDataSet,
                        expectedTableName,
                        expectedRowIndex
                );

                if (matchingActualRowsIndexes.size() == 0) {
                    throw new DbUnitDataSetComparerException(
                            "cannot find match"
                            + " for expected row [" + serializeRowForDebug(expectedTable, expectedRowIndex) + "]"
                            + ";\nactual data is:\n"
                            + serializeTable(actualDataSet, expectedTableName)
                    );
                }

                dataSetMatches = dataSetMatches.addRowMatch(
                        expectedTableName,
                        expectedRowIndex,
                        matchingActualRowsIndexes
                );
            }
        }

        return dataSetMatches;
    }

    private List<Integer> getMatchingActualRowsIndexes(@NonNull final IDataSet expectedDataSet,
                                                       @NonNull final IDataSet actualDataSet,
                                                       @NonNull final String expectedTableName,
                                                       final int expectedRowIndex) throws DataSetException {
        final List<Integer> matchingActualRowsIndexes = new ArrayList<>();

        final ITable actualTable = actualDataSet.getTable(expectedTableName);

        for (int actualRowIndex = 0; actualRowIndex < actualTable.getRowCount(); actualRowIndex++) {
            final RowMatch rowMatch = new RowMatch(expectedTableName, expectedRowIndex, actualRowIndex);

            if (this.rowComparer.rowsMatchIgnoringPlaceholders(rowMatch, expectedDataSet, actualDataSet)) {
                matchingActualRowsIndexes.add(actualRowIndex);
            }
        }

        return matchingActualRowsIndexes;
    }

    private boolean hasAtLeastOneValidMatch(@NonNull final IDataSet expectedDataSet,
                                            @NonNull final IDataSet actualDataSet,
                                            @NonNull final DataSetMatches dataSetMatches) {
        for (final DataSetMatch dataSetMatch : dataSetMatches.getMatches()) {
            if (isValidMatch(dataSetMatch, expectedDataSet, actualDataSet)) {
                return true;
            }
        }

        return false;
    }

    private boolean isValidMatch(@NonNull final DataSetMatch dataSetMatch,
                                 @NonNull final IDataSet expectedDataSet,
                                 @NonNull final IDataSet actualDataSet) {
        return actualRowsMappedOnlyOnce(dataSetMatch)
               && rowsMatchIncludingPlaceholders(dataSetMatch, expectedDataSet, actualDataSet);
    }

    private boolean actualRowsMappedOnlyOnce(@NonNull final DataSetMatch dataSetMatch) {
        final Multimap<String /*tableName*/, Integer/*actualRowIndex*/> actualRowIndexes = HashMultimap.create();

        for (final RowMatch rowMatch : dataSetMatch.getRowMatches()) {
            final String tableName = rowMatch.getTableName();
            final int actualRowIndex = rowMatch.getActualRowsIndex();

            if (actualRowIndexes.containsEntry(tableName, actualRowIndex)) {
                // this actual row is already mapped, and it's not allowed to map to multiple expected rows
                return false;
            } else {
                actualRowIndexes.put(tableName, actualRowIndex);
            }
        }

        return true;
    }

    private boolean rowsMatchIncludingPlaceholders(@NonNull final DataSetMatch dataSetMatch,
                                                   @NonNull final IDataSet expectedDataSet,
                                                   @NonNull final IDataSet actualDataSet) {
        final DbUnitPlaceholders placeholders = new DbUnitPlaceholders();

        for (final RowMatch rowMatch : dataSetMatch.getRowMatches()) {
            if (!this.rowComparer.rowsMatch(rowMatch, expectedDataSet, actualDataSet, placeholders)) {
                return false;
            }
        }

        return true;
    }

    private String serializeRowForDebug(@NonNull final ITable table,
                                        final int rowIndex) throws DataSetException {
        final StringBuilder result = new StringBuilder();

        final ITableMetaData tableMetaData = table.getTableMetaData();
        final String tableName = tableMetaData.getTableName();

        result.append("<").append(StringEscapeUtils.escapeXml11(tableName));

        final Column[] columns = tableMetaData.getColumns();
        for (final Column column : columns) {
            final String columnName = column.getColumnName();
            final Object columnValue = table.getValue(rowIndex, columnName);

            if (columnValue == null) {
                continue;
            }

            result.append(" ").append(columnName).append("=\"").append(columnValue).append("\"");
        }

        result.append(" />");

        return result.toString();
    }


    private String serializeTable(@NonNull final IDataSet actualDataSet,
                                  @NonNull final String expectedTableName) throws DataSetException {
        final StringWriter writer = new StringWriter();

        final FilteredDataSet filteredDataSet = new FilteredDataSet(new String[]{expectedTableName}, actualDataSet);

        final FlatXmlWriter xmlWriter = new FlatXmlWriter(writer);
        xmlWriter.write(filteredDataSet);

        return writer.toString();
    }

}
