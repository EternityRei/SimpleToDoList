package com.todo.list;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.list.dto.TodoDTO;
import com.todo.list.entity.Todo;
import com.todo.list.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.mockito.ArgumentMatchers.any;


import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TodoService todoService;

    @BeforeEach
    void setUp() {
        // Mock behavior for service methods
        when(todoService.getAllTodos()).thenReturn(Collections.emptyList());

        TodoDTO existingTodoDTO = new TodoDTO();
        existingTodoDTO.setId(1L);
        existingTodoDTO.setTitle("Test Todo");
        existingTodoDTO.setDescription("Testing my first todo");

        when(todoService.getTodoById(1L)).thenReturn(Optional.of(existingTodoDTO));
    }


    @Test
    public void testGetAllTodos() throws Exception {
        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.succeeded", is(true)))
                .andExpect(jsonPath("$.statusMessage", is("Lists Found")))
                .andExpect(jsonPath("$.results", hasSize(0)));

        verify(todoService, times(1)).getAllTodos();
    }

    @Test
    public void testGetTodoById() throws Exception {
        mockMvc.perform(get("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.succeeded", is(true)))
                .andExpect(jsonPath("$.statusMessage", is("List was found successfully")))
                .andExpect(jsonPath("$.results[0].id", is(1)))
                .andExpect(jsonPath("$.results[0].title", is("Test Todo")))
                .andExpect(jsonPath("$.results[0].description", is("Testing my first todo")));

        verify(todoService, times(1)).getTodoById(1L);
    }

    @Test
    public void testCreateTodo() throws Exception {
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setTitle("New Todo");
        todoDTO.setDescription("Creating a new todo");

        when(todoService.createTodo(any(TodoDTO.class))).thenReturn(todoDTO);

        ResultActions result = mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(todoDTO)));

        result.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.succeeded", is(true)))
                .andExpect(jsonPath("$.statusMessage", is("List is successfully created")))
                .andExpect(jsonPath("$.results[0].id", is(nullValue())))
                .andExpect(jsonPath("$.results[0].title", is("New Todo")))
                .andExpect(jsonPath("$.results[0].description", is("Creating a new todo")));

        verify(todoService, times(1)).createTodo(any(TodoDTO.class));
    }

    @Test
    public void testUpdateTodo() throws Exception {
        TodoDTO updatedTodoDTO = new TodoDTO();
        updatedTodoDTO.setTitle("Updated Todo");
        updatedTodoDTO.setDescription("Updating an existing todo");

        mockMvc.perform(put("/api/todos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTodoDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.succeeded", is(true)))
                .andExpect(jsonPath("$.statusMessage", is("List has been changed successfully")));

        verify(todoService, times(1)).updateTodo(eq(1L), any(TodoDTO.class));
    }

    @Test
    public void testDeleteTodo() throws Exception {
        mockMvc.perform(delete("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.succeeded", is(true)))
                .andExpect(jsonPath("$.statusMessage", containsString("List with Id 1 has been deleted")));

        verify(todoService, times(1)).deleteTodo(eq(1L));
    }

}
