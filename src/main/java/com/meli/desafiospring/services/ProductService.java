package com.meli.desafiospring.services;

import com.meli.desafiospring.exceptions.FilterNotValidException;
import com.meli.desafiospring.model.dto.ProductListDTO;

import java.util.HashMap;
import java.util.List;

public interface ProductService {

    public List<ProductListDTO> getProducts(HashMap<String, String> filters) throws FilterNotValidException;

}
