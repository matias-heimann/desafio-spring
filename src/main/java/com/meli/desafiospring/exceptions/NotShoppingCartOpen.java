package com.meli.desafiospring.exceptions;

import org.springframework.http.HttpStatus;

public class NotShoppingCartOpen extends BaseException{
    public NotShoppingCartOpen(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
