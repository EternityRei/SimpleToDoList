package com.todo.list.service;

import com.todo.list.dto.TodoDTO;
import com.todo.list.entity.Todo;
import com.todo.list.exception.EntityNotFoundException;
import com.todo.list.mapper.TodoMapper;
import com.todo.list.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.todo.list.exception.StatusCodes.ENTITY_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Service
public class TodoServiceImpl implements TodoService{

    private final TodoMapper todoMapper;
    private final TodoRepository todoRepository;

    @Override
    public List<TodoDTO> getAllTodos() {
        List<Todo> todos = todoRepository.findAll();
        return todos.stream()
                .map(todoMapper::todoToTodoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TodoDTO> getTodoById(Long id) {
        return todoRepository.findTodoById(id)
                .map(todoMapper::todoToTodoDTO);
    }

    @Override
    public TodoDTO createTodo(TodoDTO todoDTO) {
        Todo todo = todoMapper.todoDTOToTodo(todoDTO);
        if (todo != null){
            todo = todoRepository.save(todo);
        }
        return todoMapper.todoToTodoDTO(todo);
    }

    @Override
    public void updateTodo(Long id, TodoDTO todoDTO) {
        Todo todo = todoRepository.findTodoById(id)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND.name(), "Todo list not found with id: " + id));
        Todo updatedTodo = todoMapper.todoDTOToTodo(todoDTO);
        if (!todo.equals(updatedTodo)){
            todo.setTitle(updatedTodo.getTitle());
            todo.setDescription(updatedTodo.getDescription());
            todoRepository.save(todo);
        }
    }

    @Override
    public void deleteTodo(Long id) {
        Todo todo = todoRepository.findTodoById(id)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND.name(), "Todo list not found with id: " + id));
        todoRepository.delete(todo);
    }
}
