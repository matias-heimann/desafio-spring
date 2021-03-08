package com.meli.desafiospring.exceptions;

import com.meli.desafiospring.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class FilterNotValidException extends BaseException {
    public FilterNotValidException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
