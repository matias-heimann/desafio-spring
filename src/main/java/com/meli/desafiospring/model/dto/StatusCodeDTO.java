package com.meli.desafiospring.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
public class StatusCodeDTO {

    private HttpStatus code;
    private String message;

    public StatusCodeDTO(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }
}
