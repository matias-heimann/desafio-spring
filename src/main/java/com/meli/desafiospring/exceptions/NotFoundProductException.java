package com.meli.desafiospring.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundProductException extends BaseException{
    public NotFoundProductException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
