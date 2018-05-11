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

package org.garnishtest.demo.rest_complex.web.infrastructure.factories.service;

import org.garnishtest.demo.rest_complex.web.infrastructure.factories.dao.DaoMappersConfig;
import org.garnishtest.demo.rest_complex.web.infrastructure.factories.infrastructure.SecurityConfig;
import org.garnishtest.demo.rest_complex.web.service.AuthTokensService;
import org.garnishtest.demo.rest_complex.web.service.TodoListsService;
import org.garnishtest.demo.rest_complex.web.service.TokenGenerator;
import org.garnishtest.demo.rest_complex.web.service.UsersService;
import org.garnishtest.demo.rest_complex.web.service.geocoding.GeoCodingService;
import org.garnishtest.demo.rest_complex.web.service.security.CurrentUserProvider;
import org.garnishtest.modules.generic.httpclient.RestApiClientFactory;
import org.garnishtest.modules.generic.httpclient.SimpleHttpClient;
import org.garnishtest.modules.generic.httpclient.executor.HttpRequestExecutor;
import org.garnishtest.modules.generic.httpclient.request_preparer.impl.NoOpHttpRequestPreparer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        DaoMappersConfig.class,
        SecurityConfig.class
})
public class ServiceConfig {

    @Autowired
    private DaoMappersConfig daoMappersConfig;

    @Autowired
    private SecurityConfig securityConfig;

    @Value("${googleGeocodeApi.baseUrl}")
    private String googleGeocodeApiBaseUrl;

    @Bean
    public UsersService usersService() throws Exception {
        return new UsersService(
                currentUserProvider(),
                this.daoMappersConfig.userMapper(),
                this.daoMappersConfig.addressMapper(),
                this.securityConfig.passwordEncoder(),
                geoCodingService()
        );
    }

    @Bean
    public TodoListsService todoListService() throws Exception {
        return new TodoListsService(
                this.daoMappersConfig.todoListMapper()
        );
    }

    @Bean
    public AuthTokensService authTokensService() throws Exception {
        return new AuthTokensService(
                usersService(),
                this.daoMappersConfig.authTokensMapper(),
                tokenGenerator()
        );
    }

    @Bean
    public TokenGenerator tokenGenerator() {
        return new TokenGenerator();
    }

    @Bean
    public CurrentUserProvider currentUserProvider() {
        return new CurrentUserProvider();
    }

    @Bean
    public GeoCodingService geoCodingService() {
        return new GeoCodingService(
                new SimpleHttpClient(
                        this.googleGeocodeApiBaseUrl,
                        new HttpRequestExecutor(
                                new RestApiClientFactory().createClient(),
                                NoOpHttpRequestPreparer.instance()
                        )
                )
        );
    }


}
