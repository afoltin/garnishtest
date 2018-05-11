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

package org.garnishtest.demo.rest_complex.web.infrastructure.factories.web;

import org.garnishtest.demo.rest_complex.web.infrastructure.factories.service.ServiceConfig;
import org.garnishtest.demo.rest_complex.web.web.controllers.todo_lists.TodoListsController;
import org.garnishtest.demo.rest_complex.web.web.controllers.auth_tokens.AuthTokensController;
import org.garnishtest.demo.rest_complex.web.web.controllers.users.UsersController;
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
