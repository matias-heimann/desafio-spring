package com.meli.desafiospring.model;

import lombok.Getter;

@Getter
public class IndividualPurchase {

    private Integer id;
    private Integer quantity;

    public IndividualPurchase(){}

    public IndividualPurchase(Integer id, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }
}
