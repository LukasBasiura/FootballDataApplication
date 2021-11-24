package com.example.exampleproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Entity does not exists")  // 404
public class NoSuchCountryException extends RuntimeException {
    public NoSuchCountryException(String message) {
        super(message);
    }
}
