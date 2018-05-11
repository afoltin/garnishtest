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

package org.garnishtest.modules.generic.db_util.scripts;

import org.garnishtest.modules.generic.db_util.jdbc_transactions.DatabaseAction;
import org.garnishtest.modules.generic.db_util.jdbc_transactions.SimpleTransactionTemplate;
import org.garnishtest.modules.generic.variables_resolver.VariablesResolver;
import org.garnishtest.modules.generic.variables_resolver.impl.NoOpVariablesResolver;
import org.garnishtest.modules.generic.variables_resolver.impl.escape.impl.ValueEscapers;
import lombok.NonNull;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.AutoCloseInputStream;
import org.flywaydb.core.internal.dbsupport.DbSupport;
import org.flywaydb.core.internal.dbsupport.DbSupportFactory;
import org.flywaydb.core.internal.dbsupport.JdbcTemplate;
import org.flywaydb.core.internal.dbsupport.SqlScript;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Types;
import java.util.List;

public class DbScriptsExecutor {

    @NonNull private final SimpleTransactionTemplate transactionTemplate;

    public DbScriptsExecutor(@NonNull final DataSource dataSource) {
        this.transactionTemplate = new SimpleTransactionTemplate(dataSource);
    }

    public void executeScripts(@NonNull final List<? extends Resource> sqlScriptResources) throws DbScriptsExecutorException {
        executeScripts(NoOpVariablesResolver.instance(), sqlScriptResources);
    }

    public void executeScripts(@NonNull final VariablesResolver variablesResolver,
                               @NonNull final List<? extends Resource> sqlScriptResources) throws DbScriptsExecutorException {
        try {
            this.transactionTemplate.executeInTransaction(new DatabaseAction() {
                @Override
                public void doInTransaction(final Connection connection) {
                    DbScriptsExecutor.this.executeScripts(connection, variablesResolver, sqlScriptResources);
                }
            });
        } catch (final Exception e) {
            throw new DbScriptsExecutorException(
                    "failed to execute SQL scripts [" + sqlScriptResources + "]" +
                    " with variablesResolver=[" + variablesResolver + "]",
                    e
            );
        }
    }

    @SuppressWarnings("PMD.UnusedPrivateMethod") // bug in PMD - this method is being called from method above
    private void executeScripts(@NonNull final Connection connection,
                                @NonNull final VariablesResolver variablesResolver,
                                @NonNull final List<? extends Resource> sqlScriptResources) {
        final DbSupport dbSupport = createDbSupport(connection);

        for (final Resource sqlScriptResource : sqlScriptResources) {
            executeScript(variablesResolver, sqlScriptResource, dbSupport, connection);
        }
    }

    private DbSupport createDbSupport(final Connection connection) {
        final boolean printInfo = true;

        return DbSupportFactory.createDbSupport(connection, printInfo);
    }

    private void executeScript(@NonNull final VariablesResolver variablesResolver,
                               @NonNull final Resource sqlScriptResource,
                               @NonNull final DbSupport dbSupport,
                               @NonNull final Connection connection) {
        final String scriptContent = loadScriptContent(sqlScriptResource);

        final String scriptContentWithVariablesReplaced = variablesResolver.resolveVariablesInText(scriptContent, ValueEscapers.none());

        final SqlScript sqlScript = new SqlScript(scriptContentWithVariablesReplaced, dbSupport);

        sqlScript.execute(new JdbcTemplate(connection, Types.NULL));
    }

    private String loadScriptContent(@NonNull final Resource sqlScriptResource) throws DbScriptsExecutorException {
        final InputStream inputStream;
        try {
            inputStream = sqlScriptResource.getInputStream();
        } catch (final Exception e) {
            throw new DbScriptsExecutorException(
                    "failed to open stream to read content of resource [" + sqlScriptResource + "]",
                    e
            );
        }

        if (inputStream == null) {
            throw new DbScriptsExecutorException("resource [" + sqlScriptResource + "] cannot be found");
        }

        final AutoCloseInputStream autoCloseInputStream = new AutoCloseInputStream(inputStream);

        try {
            return IOUtils.toString(autoCloseInputStream, "UTF-8");
        } catch (final Exception e) {
            throw new DbScriptsExecutorException("failed to read content of resource [" + sqlScriptResource + "]", e);
        }
    }

}
