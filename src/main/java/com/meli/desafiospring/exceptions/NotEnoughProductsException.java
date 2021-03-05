package com.meli.desafiospring.exceptions;

import org.springframework.http.HttpStatus;

public class NotEnoughProductsException extends BaseException{


    public NotEnoughProductsException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
