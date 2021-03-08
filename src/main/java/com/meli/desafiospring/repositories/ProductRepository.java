package com.meli.desafiospring.repositories;

import com.meli.desafiospring.exceptions.NotFoundProductException;
import com.meli.desafiospring.model.ProductDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface ProductRepository {
    public List<ProductDAO> getAll();
    public List<ProductDAO> getWithFilterAndOrder(HashMap<String, Object> filters, Integer order);
    public List<ProductDAO> getByIds(Set<Integer> ids) throws NotFoundProductException;
}
