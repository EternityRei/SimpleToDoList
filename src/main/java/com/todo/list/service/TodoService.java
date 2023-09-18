package com.todo.list.service;

import com.todo.list.dto.TodoDTO;

import java.util.List;
import java.util.Optional;

public interface TodoService {
    List<TodoDTO> getAllTodos();
    Optional<TodoDTO> getTodoById(Long id);
    TodoDTO createTodo(TodoDTO todoDTO);
    void updateTodo(Long id, TodoDTO todoDTO);
    void deleteTodo(Long id);
}
