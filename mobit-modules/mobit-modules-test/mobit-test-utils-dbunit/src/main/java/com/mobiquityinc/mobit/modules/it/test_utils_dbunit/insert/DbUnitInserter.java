package com.mobiquityinc.mobit.modules.it.test_utils_dbunit.insert;

import com.mobiquityinc.mobit.modules.generic.db_util.jdbc_transactions.DatabaseAction;
import com.mobiquityinc.mobit.modules.generic.db_util.jdbc_transactions.SimpleTransactionTemplate;
import com.mobiquityinc.mobit.modules.generic.variables_resolver.VariablesResolver;
import com.mobiquityinc.mobit.modules.it.test_utils_dbunit.compare.utils.DbUnitXmlDataSetUtils;
import com.mobiquityinc.mobit.modules.it.test_utils_dbunit.config.DbUnitConfigurer;
import lombok.NonNull;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public final class DbUnitInserter {

    @NonNull private final SimpleTransactionTemplate transactionTemplate;
    @NonNull private final DbUnitConfigurer dbUnitConfigurer;

    public DbUnitInserter(@NonNull final DataSource dataSource,
                          @NonNull final DbUnitConfigurer dbUnitConfigurer) {
        this.transactionTemplate = new SimpleTransactionTemplate(dataSource);
        this.dbUnitConfigurer = dbUnitConfigurer;
    }

    public void insert(@NonNull final Resource dataSetResource,
                       @NonNull final VariablesResolver variablesResolver) throws DbUnitInserterException {
        if (!dataSetResource.exists()) {
            throw new IllegalArgumentException("cannot find dataSetResource [" + dataSetResource + "]");
        }
        if (!dataSetResource.isReadable()) {
            throw new IllegalArgumentException("cannot read dataSetResource [" + dataSetResource + "]");
        }

        try {
            tryToInsert(dataSetResource, variablesResolver);
        } catch (final Exception e) {
            throw new DbUnitInserterException("failed to insert to database the data from [" + dataSetResource + "]", e);
        }
    }

    private void tryToInsert(@NonNull final Resource datasetResource,
                             @NonNull final VariablesResolver variablesResolver) throws IOException, DatabaseUnitException, SQLException {
        final IDataSet dataSet = DbUnitXmlDataSetUtils.loadFromResource(datasetResource, variablesResolver);

        this.transactionTemplate.executeInTransaction(new DatabaseAction() {
            @Override
            public void doInTransaction(final Connection connection) throws DatabaseUnitException, SQLException {
                doInsertWithConnection(connection, dataSet);
            }
        });

    }

    private void doInsertWithConnection(@NonNull final Connection connection,
                                        @NonNull final IDataSet dataSet) throws DatabaseUnitException, SQLException {
        final DatabaseConnection databaseConnection = new DatabaseConnection(connection, this.dbUnitConfigurer.getDatabaseName());

        this.dbUnitConfigurer.configure(
                databaseConnection.getConfig()
        );

        DatabaseOperation.INSERT.execute(databaseConnection, dataSet);
    }

}
