package com.meli.desafiospring.utils;

import com.meli.desafiospring.model.ProductDAO;
import lombok.Getter;

@Getter
public class FilterProductRepositoryUtil {

    private ProductDAO productDAO;
    private Object filter;

    public FilterProductRepositoryUtil(ProductDAO productDAO, Object filter) {
        this.productDAO = productDAO;
        this.filter = filter;
    }
}
