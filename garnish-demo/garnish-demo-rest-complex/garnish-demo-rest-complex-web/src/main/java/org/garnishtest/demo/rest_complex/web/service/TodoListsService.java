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

package org.garnishtest.demo.rest_complex.web.service;

import org.garnishtest.demo.rest_complex.web.dao.mappers.TodoListsMapper;
import org.garnishtest.demo.rest_complex.web.dao.model.TodoList;
import lombok.NonNull;

import java.util.List;

public final class TodoListsService {

    @NonNull private final TodoListsMapper todoListsMapper;

    public TodoListsService(@NonNull final TodoListsMapper todoListsMapper) {
        this.todoListsMapper = todoListsMapper;
    }

    public List<TodoList> getAll(final long userId) {
        return this.todoListsMapper.getAll(userId);
    }

}
