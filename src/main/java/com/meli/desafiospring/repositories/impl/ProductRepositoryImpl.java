package com.meli.desafiospring.repositories.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.desafiospring.model.ProductDAO;
import com.meli.desafiospring.repositories.ProductRepository;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private HashMap<Integer, ProductDAO> products;

    public ProductRepositoryImpl() throws IOException {
        products = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductDAO> productList = objectMapper.readValue(new File("src/main/resources/static/products.json"),
                new TypeReference<List<ProductDAO>>(){});
        for(ProductDAO productDAO: productList){
            products.put(productDAO.getId(), productDAO);
        }
    }

}
