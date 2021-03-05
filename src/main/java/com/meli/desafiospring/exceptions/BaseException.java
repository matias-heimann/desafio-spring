package com.meli.desafiospring.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BaseException extends Exception{

    private String message;
    private HttpStatus status;

    public BaseException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
