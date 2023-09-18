package com.todo.list.controller;

import com.todo.list.dto.RemoteResponse;
import com.todo.list.dto.TodoDTO;
import com.todo.list.exception.EntityNotFoundException;
import com.todo.list.exception.StatusCodes;
import com.todo.list.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.todo.list.exception.StatusCodes.ENTITY_NOT_FOUND;
import static com.todo.list.exception.StatusCodes.OK;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    /**
     * Get a list of all todo items.
     *
     * @return List of TodoDTO objects
     */
    @GetMapping
    public ResponseEntity<RemoteResponse> getAllTodos() {
        log.info("Getting all existing lists");
        List<TodoDTO> todos = todoService.getAllTodos();
        RemoteResponse remoteResponse = RemoteResponse.create(true, OK.name(), "Lists Found", todos);
        return ResponseEntity.ok().body(remoteResponse);
    }

    /**
     * Get a specific todo item by ID.
     *
     * @param id ID of the todo item to retrieve
     * @return TodoDTO object if found, 404 Not Found if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<RemoteResponse> getTodoById(@PathVariable Long id) {
        TodoDTO todo = todoService.getTodoById(id).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND.name(), "List was not found"));
        RemoteResponse successfulResponse = RemoteResponse.create(true, OK.name(), "List was found successfully", List.of(todo));
        return ResponseEntity.status(HttpStatus.OK).body(successfulResponse);
    }

    /**
     * Create a new todo item.
     *
     * @param todoDTO TodoDTO object containing the data for the new todo item
     * @return TodoDTO object of the created todo item
     */
    @PostMapping
    public ResponseEntity<RemoteResponse> createTodo(@RequestBody TodoDTO todoDTO) {
        log.info("Creating a new list");
        TodoDTO createdTodo = todoService.createTodo(todoDTO);
        log.info("List created successfully");
        RemoteResponse successfulResponse = RemoteResponse.create(true, OK.name(), "List is successfully created", List.of(createdTodo));
        return ResponseEntity.status(HttpStatus.CREATED).body(successfulResponse);
    }

    /**
     * Update an existing todo item.
     *
     * @param todoDTO TodoDTO object containing the updated data
     * @return TodoDTO object of the updated todo item, 404 Not Found if the todo item doesn't exist
     */
    @PutMapping("/{id}")
    public ResponseEntity<RemoteResponse> updateTodo(@PathVariable Long id, @RequestBody TodoDTO todoDTO) {
        log.info("Updating the list with id {}", id);
        todoService.updateTodo(id, todoDTO);
        log.info("List with id {} was updated successfully", id);
        RemoteResponse remoteResponse = RemoteResponse.create(true, OK.name(), "List has been changed successfully", null);
        return ResponseEntity.ok().body(remoteResponse);
    }

    /**
     * Delete a todo item by ID.
     *
     * @param id ID of the todo item to delete
     * @return 204 No Content if successful, 404 Not Found if the todo item doesn't exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<RemoteResponse> deleteTodo(@PathVariable Long id) {
        log.info("Deleting list with id {}", id);
        todoService.deleteTodo(id);
        RemoteResponse remoteResponse = RemoteResponse.create(true, OK.name(),
                String.format("List with Id %s has been deleted", id), null);
        return ResponseEntity.ok().body(remoteResponse);
    }
}

