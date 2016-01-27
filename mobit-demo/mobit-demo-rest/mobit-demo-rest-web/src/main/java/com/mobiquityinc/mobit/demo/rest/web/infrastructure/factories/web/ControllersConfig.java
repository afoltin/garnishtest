package com.mobiquityinc.mobit.demo.rest.web.infrastructure.factories.web;

import com.mobiquityinc.mobit.demo.rest.web.infrastructure.factories.service.ServiceConfig;
import com.mobiquityinc.mobit.demo.rest.web.web.controllers.todo_lists.TodoListsController;
import com.mobiquityinc.mobit.demo.rest.web.web.controllers.auth_tokens.AuthTokensController;
import com.mobiquityinc.mobit.demo.rest.web.web.controllers.users.UsersController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ServiceConfig.class)
public class ControllersConfig {

    @Autowired
    private ServiceConfig serviceConfig;

    @Bean
    public TodoListsController todoListController() throws Exception {
        return new TodoListsController(
                this.serviceConfig.todoListService(),
                this.serviceConfig.currentUserProvider()
        );
    }

    @Bean
    public UsersController usersController() throws Exception {
        return new UsersController(
                this.serviceConfig.usersService()
        );
    }

    @Bean
    public AuthTokensController authTokensController() throws Exception {
        return new AuthTokensController(
                this.serviceConfig.authTokensService()
        );
    }

}
