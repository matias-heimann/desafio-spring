package com.meli.desafiospring.exceptions;

import org.springframework.http.HttpStatus;

public class NotValidInformationForNewUserException extends BaseException{
    public NotValidInformationForNewUserException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
