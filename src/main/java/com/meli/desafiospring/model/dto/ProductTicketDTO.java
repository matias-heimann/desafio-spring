package com.meli.desafiospring.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductTicketDTO {

    private Integer id;
    private String name;
    private String brand;
    private Integer quantity;

    public ProductTicketDTO(Integer id, String name, String brand, Integer quantity) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.quantity = quantity;
    }
}
