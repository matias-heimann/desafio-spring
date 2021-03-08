package com.meli.desafiospring.services.impl;

import com.meli.desafiospring.exceptions.NotEnoughProductsException;
import com.meli.desafiospring.exceptions.NotFoundProductException;
import com.meli.desafiospring.model.IndividualPurchase;
import com.meli.desafiospring.model.ProductDAO;
import com.meli.desafiospring.model.PurchaseArticles;
import com.meli.desafiospring.model.dto.BuyOrderDTO;
import com.meli.desafiospring.model.dto.ProductListDTO;
import com.meli.desafiospring.model.dto.ProductTicketDTO;
import com.meli.desafiospring.model.dto.StatusCodeDTO;
import com.meli.desafiospring.repositories.ProductRepository;
import com.meli.desafiospring.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    private Integer purchases;

    public ProductServiceImpl(){
        this.purchases = 0;
    }

    @Override
    public List<ProductListDTO> getProducts(HashMap<String, Object> filters, Integer order) {
        return this.productRepository
                .getWithFilterAndOrder(filters, order)
                .stream()
                .map(p -> new ProductListDTO(p)).collect(Collectors.toList());
    }

}
