package com.meli.desafiospring.model;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductDAO {

    private Integer id;
    private String name;
    private String category;
    private String brand;
    private Integer price;
    private Boolean freeShiping;
    private Integer quantity;
    private Integer prestige;

    public ProductDAO(){

    }

    @Override
    public String toString() {
        return "ProductDAO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", freeShiping=" + freeShiping +
                ", quantity=" + quantity +
                ", prestige=" + prestige +
                '}';
    }
}
