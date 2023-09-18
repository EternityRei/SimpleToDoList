package com.todo.list.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityNotFoundException extends BaseException {

    public EntityNotFoundException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
