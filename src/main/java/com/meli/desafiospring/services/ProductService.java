package com.meli.desafiospring.services;

import com.meli.desafiospring.exceptions.NotFoundProductException;
import com.meli.desafiospring.model.PurchaseArticles;
import com.meli.desafiospring.model.dto.BuyOrderDTO;
import com.meli.desafiospring.model.dto.ProductListDTO;
import com.meli.desafiospring.model.dto.ProductTicketDTO;

import java.util.HashMap;
import java.util.List;

public interface ProductService {

    public List<ProductListDTO> getProducts(HashMap<String, Object> filters, Integer order);
    public BuyOrderDTO purchaseItems(PurchaseArticles products) throws NotFoundProductException;

}
