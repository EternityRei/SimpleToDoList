package com.todo.list.mapper;

import com.todo.list.dto.TodoDTO;
import com.todo.list.entity.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface TodoMapper {
    TodoMapper INSTANCE = Mappers.getMapper(TodoMapper.class);

    TodoDTO todoToTodoDTO(Todo todo);
    List<TodoDTO> todoListToTodoDTOList(List<Todo> todos);
    Todo todoDTOToTodo(TodoDTO todoDTO);
}
