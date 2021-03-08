package com.meli.desafiospring.model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

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
}
