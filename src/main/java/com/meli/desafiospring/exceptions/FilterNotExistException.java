package com.meli.desafiospring.exceptions;

import com.meli.desafiospring.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class FilterNotExistException extends BaseException {
    public FilterNotExistException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
