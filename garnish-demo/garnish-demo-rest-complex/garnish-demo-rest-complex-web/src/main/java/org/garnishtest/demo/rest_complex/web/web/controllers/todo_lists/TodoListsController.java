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

package org.garnishtest.demo.rest_complex.web.web.controllers.todo_lists;

import com.google.common.collect.ImmutableList;
import org.garnishtest.demo.rest_complex.web.dao.model.TodoList;
import org.garnishtest.demo.rest_complex.web.service.TodoListsService;
import org.garnishtest.demo.rest_complex.web.service.security.CurrentUserProvider;
import org.garnishtest.demo.rest_complex.web.service.security.SecurityUser;
import org.garnishtest.demo.rest_complex.web.web.model.TodoListApi;
import lombok.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/todo-list")
public final class TodoListsController {

    @NonNull private final TodoListsService todoListsService;
    @NonNull private final CurrentUserProvider currentUserProvider;

    public TodoListsController(@NonNull final TodoListsService todoListsService,
                               @NonNull final CurrentUserProvider currentUserProvider) {
        this.todoListsService = todoListsService;
        this.currentUserProvider = currentUserProvider;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public List<TodoListApi> getLists() {
        final SecurityUser currentUser = this.currentUserProvider.getCurrentUser();
        if (currentUser == null) {
            throw new RuntimeException("403"); // todo: proper json exception handling
        }

        final ImmutableList.Builder<TodoListApi> builder = ImmutableList.builder();

        final List<TodoList> todoLists = this.todoListsService.getAll(currentUser.getId());
        for (final TodoList todoList : todoLists) {
            builder.add(
                    new TodoListApi(todoList.getId(), todoList.getTitle())
            );
        }

        return builder.build();
    }

}
