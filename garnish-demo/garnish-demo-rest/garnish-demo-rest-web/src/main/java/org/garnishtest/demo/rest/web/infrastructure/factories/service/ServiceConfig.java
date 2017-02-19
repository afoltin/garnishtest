package org.garnishtest.demo.rest.web.infrastructure.factories.service;

import org.garnishtest.demo.rest.web.infrastructure.factories.dao.DaoMappersConfig;
import org.garnishtest.demo.rest.web.infrastructure.factories.infrastructure.SecurityConfig;
import org.garnishtest.demo.rest.web.service.AuthTokensService;
import org.garnishtest.demo.rest.web.service.TodoListsService;
import org.garnishtest.demo.rest.web.service.TokenGenerator;
import org.garnishtest.demo.rest.web.service.UsersService;
import org.garnishtest.demo.rest.web.service.geocoding.GeoCodingService;
import org.garnishtest.demo.rest.web.service.security.CurrentUserProvider;
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
