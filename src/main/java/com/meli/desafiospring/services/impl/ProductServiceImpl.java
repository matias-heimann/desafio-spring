package com.meli.desafiospring.services.impl;

import com.meli.desafiospring.model.dto.ProductDTO;
import com.meli.desafiospring.repositories.ProductRepository;
import com.meli.desafiospring.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.el.LambdaExpression;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductDTO> getProducts(HashMap<String, Object> filters, Integer order) {
        return this.productRepository
                .getWithFilterAndOrder(filters, order)
                .stream()
                .map(p -> new ProductDTO(p)).collect(Collectors.toList());
    }
}
