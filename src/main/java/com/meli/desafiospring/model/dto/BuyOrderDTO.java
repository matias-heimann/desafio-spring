package com.meli.desafiospring.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class BuyOrderDTO {

    private Integer id;
    private List<ProductTicketDTO> articles;
    private Integer total;
    private StatusCodeDTO statusCodeDTO;

    public BuyOrderDTO(Integer id, List<ProductTicketDTO> articles, Integer total, StatusCodeDTO statusCodeDTO) {
        this.id = id;
        this.articles = articles;
        this.total = total;
        this.statusCodeDTO = statusCodeDTO;
    }
}
