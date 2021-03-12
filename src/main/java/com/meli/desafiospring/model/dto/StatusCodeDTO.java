package com.meli.desafiospring.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Objects;

@Getter @Setter
public class StatusCodeDTO {

    private HttpStatus code;
    private String message;

    public StatusCodeDTO(){}

    public StatusCodeDTO(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusCodeDTO that = (StatusCodeDTO) o;
        return code == that.code && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message);
    }
}
