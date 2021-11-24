package com.example.exampleproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Entity does not exists")  // 422
public class UpdateException extends RuntimeException {

    public UpdateException(String message) {
        super(message);
    }

}
