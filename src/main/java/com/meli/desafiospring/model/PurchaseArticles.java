package com.meli.desafiospring.model;

import com.meli.desafiospring.model.dto.ProductTicketDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PurchaseArticles {
    private List<ProductTicketDTO> articles;

    public PurchaseArticles(){}

    public PurchaseArticles(List<ProductTicketDTO> articles) {
        this.articles = articles;
    }
}
