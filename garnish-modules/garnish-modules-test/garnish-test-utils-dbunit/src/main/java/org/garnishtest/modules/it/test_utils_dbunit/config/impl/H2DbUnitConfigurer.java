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

package org.garnishtest.modules.it.test_utils_dbunit.config.impl;

import lombok.NonNull;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.garnishtest.modules.it.test_utils_dbunit.config.DbUnitConfigurer;

import javax.annotation.Nullable;

public final class H2DbUnitConfigurer implements DbUnitConfigurer {

    @Nullable private final String databaseName;

    public H2DbUnitConfigurer(@Nullable final String databaseName) {
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
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new H2DataTypeFactory());
    }

}
