package com.mobiquityinc.mobit.demo.rest.web.infrastructure.factories.service;

import com.mobiquityinc.mobit.demo.rest.web.infrastructure.factories.dao.DaoMappersConfig;
import com.mobiquityinc.mobit.demo.rest.web.infrastructure.factories.infrastructure.SecurityConfig;
import com.mobiquityinc.mobit.demo.rest.web.service.AuthTokensService;
import com.mobiquityinc.mobit.demo.rest.web.service.TodoListsService;
import com.mobiquityinc.mobit.demo.rest.web.service.TokenGenerator;
import com.mobiquityinc.mobit.demo.rest.web.service.UsersService;
import com.mobiquityinc.mobit.demo.rest.web.service.geocoding.GeoCodingService;
import com.mobiquityinc.mobit.demo.rest.web.service.security.CurrentUserProvider;
import com.mobiquityinc.mobit.modules.generic.httpclient.RestApiClientFactory;
import com.mobiquityinc.mobit.modules.generic.httpclient.SimpleHttpClient;
import com.mobiquityinc.mobit.modules.generic.httpclient.executor.HttpRequestExecutor;
import com.mobiquityinc.mobit.modules.generic.httpclient.request_preparer.impl.NoOpHttpRequestPreparer;
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
