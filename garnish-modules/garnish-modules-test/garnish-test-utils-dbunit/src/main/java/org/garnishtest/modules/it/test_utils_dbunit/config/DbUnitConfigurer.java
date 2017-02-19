package org.garnishtest.modules.it.test_utils_dbunit.config;

import lombok.NonNull;
import org.dbunit.database.DatabaseConfig;

import javax.annotation.Nullable;

public interface DbUnitConfigurer {

    @Nullable String getDatabaseName();

    void configure(@NonNull DatabaseConfig config);

}
