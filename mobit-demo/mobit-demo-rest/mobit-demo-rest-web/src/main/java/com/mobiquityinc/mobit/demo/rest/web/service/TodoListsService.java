package com.mobiquityinc.mobit.demo.rest.web.service;

import com.mobiquityinc.mobit.demo.rest.web.dao.mappers.TodoListsMapper;
import com.mobiquityinc.mobit.demo.rest.web.dao.model.TodoList;
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
