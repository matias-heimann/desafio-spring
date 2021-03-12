package com.meli.desafiospring.model.dto;

import com.meli.desafiospring.model.ProductDAO;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

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

    public ProductListDTO(){}

    public ProductListDTO(Integer id, String name, String category, String brand, Integer price, Boolean freeShiping, Integer quantity, Integer prestige) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.price = price;
        this.freeShiping = freeShiping;
        this.quantity = quantity;
        this.prestige = prestige;
    }

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

    @Override
    public String toString() {
        return "ProductListDTO{" +
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
        ProductListDTO that = (ProductListDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(category, that.category) && Objects.equals(brand, that.brand) && Objects.equals(price, that.price) && Objects.equals(freeShiping, that.freeShiping) && Objects.equals(quantity, that.quantity) && Objects.equals(prestige, that.prestige);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, brand, price, freeShiping, quantity, prestige);
    }
}
