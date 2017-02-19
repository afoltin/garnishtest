package com.mobiquityinc.mobit.modules.it.test_utils_dbunit.config.impl;

import com.mobiquityinc.mobit.modules.it.test_utils_dbunit.config.DbUnitConfigurer;
import lombok.NonNull;
import org.dbunit.database.DatabaseConfig;

import javax.annotation.Nullable;

public final class NoOpDbUnitConfigurer implements DbUnitConfigurer {

    @Nullable
    @Override
    public String getDatabaseName() {
        return null;
    }

    @Override
    public void configure(@NonNull final DatabaseConfig config) { }

}
