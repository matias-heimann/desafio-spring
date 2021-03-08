package com.meli.desafiospring.model;

import com.meli.desafiospring.model.dto.ProductTicketDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PurchaseArticles {
    private List<IndividualPurchase> articles;

    public PurchaseArticles(){}

    public PurchaseArticles(List<IndividualPurchase> articles) {
        this.articles = articles;
    }
}
