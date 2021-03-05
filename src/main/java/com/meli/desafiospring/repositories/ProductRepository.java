package com.meli.desafiospring.repositories;

import com.meli.desafiospring.exceptions.NotFoundProductException;
import com.meli.desafiospring.model.ProductDAO;

import java.util.HashMap;
import java.util.List;

public interface ProductRepository {
    public List<ProductDAO> getAll();
    public List<ProductDAO> getWithFilterAndOrder(HashMap<String, Object> filters, Integer order);
    public List<ProductDAO> getByIds(List<Integer> ids) throws NotFoundProductException;
}
