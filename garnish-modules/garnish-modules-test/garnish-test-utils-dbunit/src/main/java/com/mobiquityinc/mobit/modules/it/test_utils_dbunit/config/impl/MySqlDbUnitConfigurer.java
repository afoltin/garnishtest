package com.mobiquityinc.mobit.modules.it.test_utils_dbunit.config.impl;

import com.mobiquityinc.mobit.modules.it.test_utils_dbunit.config.DbUnitConfigurer;
import lombok.NonNull;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.ext.mysql.MySqlMetadataHandler;

import javax.annotation.Nullable;

public final class MySqlDbUnitConfigurer implements DbUnitConfigurer {

    @Nullable private final String databaseName;

    public MySqlDbUnitConfigurer(@Nullable final String databaseName) {
        this.databaseName = databaseName;
    }

    @Override
    @Nullable
    public String getDatabaseName() {
        return databaseName;
    }

    @Override
    public void configure(@NonNull final DatabaseConfig config) {
        config.setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, true);
        config.setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);
        config.setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());
    }

}
