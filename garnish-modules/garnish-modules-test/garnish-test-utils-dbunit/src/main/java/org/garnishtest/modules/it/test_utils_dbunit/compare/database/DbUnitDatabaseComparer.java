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

package org.garnishtest.modules.it.test_utils_dbunit.compare.database;

import org.garnishtest.modules.generic.variables_resolver.VariablesResolver;
import org.garnishtest.modules.generic.variables_resolver.impl.escape.impl.ValueEscapers;
import org.garnishtest.modules.it.test_utils_dbunit.compare.dataset.DbUnitDataSetComparer;
import org.garnishtest.modules.it.test_utils_dbunit.config.DbUnitConfigurer;
import lombok.NonNull;
import org.apache.commons.io.IOUtils;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;

public final class DbUnitDatabaseComparer {

    @NonNull private final DbUnitDataSetComparer dataSetComparer;

    @NonNull private final DataSource dataSource;
    @NonNull private final DbUnitConfigurer dbUnitConfigurer;

    public DbUnitDatabaseComparer(@NonNull final DbUnitDataSetComparer dataSetComparer,
                                  @NonNull final DataSource dataSource,
                                  @NonNull final DbUnitConfigurer dbUnitConfigurer) {
        this.dataSetComparer = dataSetComparer;

        this.dataSource = dataSource;
        this.dbUnitConfigurer = dbUnitConfigurer;
    }

    public void compareDatabaseWith(@NonNull final Resource expectedXmlDataSetResource,
                                    @NonNull final VariablesResolver variablesResolver) {
        try {
            tryToCompareDatabaseWith(expectedXmlDataSetResource, variablesResolver);
        } catch (final Exception e) {
            // since AssertionError is an Error and not an Exception, it will not be caught here,
            // and allowed to propagate to the caller, which is what we want.

            throw new DbUnitDatabaseComparerException("failed to compare database with [" + expectedXmlDataSetResource + "]", e);
        }
    }

    private void tryToCompareDatabaseWith(@NonNull final Resource expectedXmlDataSetResource,
                                          @NonNull final VariablesResolver variablesResolver) throws Exception {
        if (!expectedXmlDataSetResource.exists()) {
            throw new IllegalArgumentException("cannot find expectedXmlDataSetResource [" + expectedXmlDataSetResource + "]");
        }
        if (!expectedXmlDataSetResource.isReadable()) {
            throw new IllegalArgumentException("cannot read expectedXmlDataSetResource [" + expectedXmlDataSetResource + "]");
        }

        try (final Connection connection = this.dataSource.getConnection()) {
            final IDataSet databaseDataSet = getDatabaseDataSet(connection);
            final IDataSet expectedXmlDataSet = getExpectedXmlDataSet(expectedXmlDataSetResource, databaseDataSet, variablesResolver);

            this.dataSetComparer.compareDataSets(expectedXmlDataSet, databaseDataSet);
        }
    }

    private IDataSet getDatabaseDataSet(final Connection connection) throws DatabaseUnitException, SQLException {
        final DatabaseConnection databaseConnection = new DatabaseConnection(connection, this.dbUnitConfigurer.getDatabaseName());

        this.dbUnitConfigurer.configure(
                databaseConnection.getConfig()
        );

        return databaseConnection.createDataSet();
    }


    @NonNull
    private IDataSet getExpectedXmlDataSet(@NonNull final Resource expectedXmlDataSetResource,
                                           @NonNull final IDataSet databaseDataSet,
                                           @NonNull final VariablesResolver variablesResolver) throws IOException, DataSetException {
        final String expectedXmlContent = IOUtils.toString(
                expectedXmlDataSetResource.getInputStream(),
                StandardCharsets.UTF_8
        );

        final String expectedXmlContentWithVariablesResolved = variablesResolver.resolveVariablesInText(
                expectedXmlContent,
                ValueEscapers.xml()
        );

        return new FlatXmlDataSetBuilder()
                .setMetaDataSet(databaseDataSet)
                .setCaseSensitiveTableNames(true)
                .build(
                        new StringReader(expectedXmlContentWithVariablesResolved)
                );
    }

}

