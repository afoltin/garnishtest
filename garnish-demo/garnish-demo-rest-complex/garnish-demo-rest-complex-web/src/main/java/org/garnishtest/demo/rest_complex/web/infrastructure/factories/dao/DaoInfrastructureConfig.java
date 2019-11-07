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

package org.garnishtest.demo.rest_complex.web.infrastructure.factories.dao;

import com.p6spy.engine.spy.P6SpyDriver;
import lombok.NonNull;
import org.apache.ibatis.session.SqlSessionFactory;
import org.h2.tools.Server;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DaoInfrastructureConfig {

    @Value("classpath:/mappers/**/*.xml")
    private Resource[] mapperLocations;

    @Value("${h2Server.port}")
    private String h2ServerPort;

    @Value("${h2WebConsole.port}")
    private String h2WebConsolePort;

    @Bean
    @NonNull
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        return sqlSessionFactoryBean().getObject();
    }

    @Bean
    @NonNull
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws SQLException {
        final SqlSessionFactoryBean factory = new SqlSessionFactoryBean();

        factory.setDataSource(dataSource());
        factory.setMapperLocations(this.mapperLocations);
        factory.setTypeAliasesPackage("org.garnishtest.demo.rest_complex.web.dao.model");

        return factory;
    }

    @Bean
    @NonNull
    public DataSource dataSource() throws SQLException {
        final DataSource dataSource = createDataSource();

        populateDatabase(dataSource);

        return dataSource;
    }

    @NonNull
    // obviously, in production we would not use an in-memory database
    private DataSource createDataSource() throws SQLException {
        final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(P6SpyDriver.class);
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setUrl("jdbc:p6spy:h2:mem:garnish-demo-rest-complex;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false;INIT=CREATE SCHEMA IF NOT EXISTS garnish_demo_rest_complex_schema\\; SET SCHEMA garnish_demo_rest_complex_schema");

        return dataSource;
    }

    private void populateDatabase(final DataSource dataSource) throws SQLException {
        final ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(
                new ClassPathResource("db/garnish-demo-rest-complex-db-schema.sql")
        );

        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
    }

    // H2 database server (allows to connect outside of this process, using JDBC)
    // use url: jdbc:h2:tcp://localhost:9092/mem:garnish-demo-rest-complex
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws SQLException {
        return Server.createTcpServer("-tcp","-tcpAllowOthers","-tcpPort", this.h2ServerPort);
    }

    // H2 web console (quick web console, PhpMyAdmin style)
    // use url: jdbc:h2:tcp://localhost:9092/mem:garnish-demo-rest-complex
    // obviously, you wouldn't want to expose something like this in production
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2WebConsole() throws SQLException {
        return Server.createWebServer("-web","-webAllowOthers","-webPort", this.h2WebConsolePort);
    }

}
