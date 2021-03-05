package com.meli.desafiospring.services;

import com.meli.desafiospring.model.dto.ProductDTO;

import java.util.HashMap;
import java.util.List;

public interface ProductService {

    public List<ProductDTO> getProducts(HashMap<String, Object> filters, Integer order);

}
