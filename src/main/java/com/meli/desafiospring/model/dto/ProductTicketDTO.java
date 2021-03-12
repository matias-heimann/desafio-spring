package com.meli.desafiospring.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter @Setter
public class ProductTicketDTO {

    private Integer id;
    private String name;
    private String brand;
    private Integer quantity;

    public ProductTicketDTO(){}

    public ProductTicketDTO(Integer id, String name, String brand, Integer quantity) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductTicketDTO that = (ProductTicketDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(brand, that.brand) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, brand, quantity);
    }
}
