package org.garnishtest.demo.rest_complex.web.dao.mappers;

import org.garnishtest.demo.rest_complex.web.dao.model.TodoList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TodoListsMapper {

    List<TodoList> getAll(@Param("userId") long userId);

}
