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

import org.garnishtest.demo.rest_complex.web.dao.mappers.AddressesMapper;
import org.garnishtest.demo.rest_complex.web.dao.mappers.AuthTokensMapper;
import org.garnishtest.demo.rest_complex.web.dao.mappers.TodoListsMapper;
import org.garnishtest.demo.rest_complex.web.dao.mappers.UsersMapper;
import lombok.NonNull;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DaoInfrastructureConfig.class)
public class DaoMappersConfig {

    @Autowired
    private DaoInfrastructureConfig daoInfrastructureConfig;

    @Bean
    public TodoListsMapper todoListMapper() throws Exception {
        return createMapper(TodoListsMapper.class);
    }

    @Bean
    public UsersMapper userMapper() throws Exception {
        return createMapper(UsersMapper.class);
    }

    @Bean
    public AddressesMapper addressMapper() throws Exception {
        return createMapper(AddressesMapper.class);
    }

    @Bean
    public AuthTokensMapper authTokensMapper() throws Exception {
        return createMapper(AuthTokensMapper.class);
    }

    private <T> T createMapper(@NonNull final Class<T> mapperClass) throws Exception {
        try (SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(
            this.daoInfrastructureConfig.sqlSessionFactory())) {

            return sessionTemplate.getMapper(mapperClass);
        }
    }

}
