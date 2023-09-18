package com.todo.list.dto;

import lombok.Data;

@Data
public class TodoDTO {
    private Long id;
    private String title;
    private String description;
}
