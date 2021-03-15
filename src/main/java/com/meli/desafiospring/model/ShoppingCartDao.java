package com.meli.desafiospring.model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Getter @Setter
public class ShoppingCartDao {

    private Integer id;
    private List<IndividualPurchase> articles;
    private Boolean isClosed;

    public ShoppingCartDao(){
        this.articles = new LinkedList<>();
        this.isClosed = false;
    }

    public ShoppingCartDao(Integer id, List<IndividualPurchase> articles) {
        this.id = id;
        this.articles = articles;
        this.isClosed = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCartDao that = (ShoppingCartDao) o;
        return Objects.equals(id, that.id) && Objects.equals(articles, that.articles) && Objects.equals(isClosed, that.isClosed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, articles, isClosed);
    }
}
