package com.meli.desafiospring.model.dto;

import com.meli.desafiospring.model.ProductDAO;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductListDTO {

    private Integer id;
    private String name;
    private String category;
    private String brand;
    private Integer price;
    private Boolean freeShiping;
    private Integer quantity;
    private Integer prestige;

    public ProductListDTO(ProductDAO product){
        this.id = product.getId();
        this.name = product.getName();
        this.category = product.getCategory();
        this.brand = product.getBrand();
        this.price = product.getPrice();
        this.freeShiping = product.getFreeShiping();
        this.quantity = product.getQuantity();
        this.prestige = product.getPrestige();
    }

}
