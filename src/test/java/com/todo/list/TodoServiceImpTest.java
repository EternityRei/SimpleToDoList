package com.todo.list;

import com.todo.list.dto.TodoDTO;
import com.todo.list.entity.Todo;
import com.todo.list.exception.EntityNotFoundException;
import com.todo.list.mapper.TodoMapper;
import com.todo.list.repository.TodoRepository;
import com.todo.list.service.TodoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TodoServiceImplTest {

    @InjectMocks
    private TodoServiceImpl todoService;

    @Mock
    private TodoMapper todoMapper;

    @Mock
    private TodoRepository todoRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTodoById() {
        // Prepare test data
        Long todoId = 1L;
        Todo todoEntity = new Todo();
        TodoDTO todoDTO = new TodoDTO();

        // Mock behavior
        when(todoRepository.findTodoById(todoId)).thenReturn(Optional.of(todoEntity));
        when(todoMapper.todoToTodoDTO(todoEntity)).thenReturn(todoDTO);

        // Call the service method
        Optional<TodoDTO> result = todoService.getTodoById(todoId);

        // Assertions
        assertTrue(result.isPresent());
        assertEquals(todoDTO, result.get());

        // Verify interactions
        verify(todoRepository, times(1)).findTodoById(todoId);
        verify(todoMapper, times(1)).todoToTodoDTO(todoEntity);
    }

    @Test
    public void testCreateTodo() {
        // Prepare test data
        TodoDTO inputTodoDTO = new TodoDTO();
        Todo todoEntity = new Todo();
        TodoDTO outputTodoDTO = new TodoDTO();

        // Mock behavior
        when(todoMapper.todoDTOToTodo(inputTodoDTO)).thenReturn(todoEntity);
        when(todoRepository.save(todoEntity)).thenReturn(todoEntity);
        when(todoMapper.todoToTodoDTO(todoEntity)).thenReturn(outputTodoDTO);

        // Call the service method
        TodoDTO result = todoService.createTodo(inputTodoDTO);

        // Assertions
        assertNotNull(result);
        assertEquals(outputTodoDTO, result);

        // Verify interactions
        verify(todoMapper, times(1)).todoDTOToTodo(inputTodoDTO);
        verify(todoRepository, times(1)).save(todoEntity);
        verify(todoMapper, times(1)).todoToTodoDTO(todoEntity);
    }

    @Test
    public void testUpdateTodoWhenTodoNotFound() {
        // Prepare test data
        Long todoId = 1L;
        TodoDTO inputTodoDTO = new TodoDTO();

        // Mock behavior for findTodoById to return an empty Optional
        when(todoRepository.findTodoById(todoId)).thenReturn(Optional.empty());

        // Call the service method and expect an EntityNotFoundException
        assertThrows(EntityNotFoundException.class, () -> todoService.updateTodo(todoId, inputTodoDTO));

        // Verify interactions
        verify(todoRepository, times(1)).findTodoById(todoId);
        verifyNoMoreInteractions(todoRepository); // Ensure no further interactions with the repository
    }

    @Test
    public void testDeleteTodo() {
        // Prepare test data
        Long todoId = 1L;
        Todo existingTodoEntity = new Todo();

        // Mock behavior
        when(todoRepository.findTodoById(todoId)).thenReturn(Optional.of(existingTodoEntity));

        // Call the service method
        todoService.deleteTodo(todoId);

        // Verify interactions
        verify(todoRepository, times(1)).findTodoById(todoId);
        verify(todoRepository, times(1)).delete(existingTodoEntity);
    }
}

