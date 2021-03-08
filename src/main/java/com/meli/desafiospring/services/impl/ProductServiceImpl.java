package com.meli.desafiospring.services.impl;

import com.meli.desafiospring.exceptions.FilterNotValidException;
import com.meli.desafiospring.model.dto.ProductListDTO;
import com.meli.desafiospring.repositories.ProductRepository;
import com.meli.desafiospring.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductListDTO> getProducts(HashMap<String, String> filters) throws FilterNotValidException {
        int order = 4;
        if(filters.get("order") != null){
            try {
                order = Integer.valueOf(filters.get("order"));
                if(order > 4){
                    throw new FilterNotValidException("The order value must be 0, 1, 2, 3 or 4");
                }
            }
            catch (NumberFormatException e){
                throw new FilterNotValidException("The order value must be 0, 1, 2, 3 or 4");
            }
            filters.remove("order");
        }
        return this.productRepository
                .getWithFilterAndOrder(filters, order)
                .stream()
                .map(p -> new ProductListDTO(p)).collect(Collectors.toList());
    }

}
