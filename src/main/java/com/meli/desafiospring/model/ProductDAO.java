package com.meli.desafiospring.model;


import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDAO that = (ProductDAO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(category, that.category) && Objects.equals(brand, that.brand) && Objects.equals(price, that.price) && Objects.equals(freeShiping, that.freeShiping) && Objects.equals(quantity, that.quantity) && Objects.equals(prestige, that.prestige);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, brand, price, freeShiping, quantity, prestige);
    }
}


