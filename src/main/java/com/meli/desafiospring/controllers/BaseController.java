package com.meli.desafiospring.controllers;

import com.meli.desafiospring.exceptions.BaseException;
import com.meli.desafiospring.model.dto.StatusCodeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class BaseController {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<StatusCodeDTO> handleException(BaseException baseException){
        return new ResponseEntity<>(new StatusCodeDTO(baseException.getStatus(), baseException.getMessage()), baseException.getStatus());
    }
}
