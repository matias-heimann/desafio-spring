package com.meli.desafiospring.utils;

import com.meli.desafiospring.model.ProductDAO;
import lombok.Getter;

@Getter
public class FilterRepositoryUtil {

    private ProductDAO productDAO;
    private Object filter;

    public FilterRepositoryUtil(ProductDAO productDAO, Object filter) {
        this.productDAO = productDAO;
        this.filter = filter;
    }
}
