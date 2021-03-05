package com.meli.desafiospring.model.dto;

import lombok.Getter;

@Getter
public class ErrorDTO {

    private String title;
    private String message;

    public ErrorDTO(String title, String message) {
        this.title = title;
        this.message = message;
    }
}
