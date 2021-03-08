package com.meli.desafiospring.utils;

import com.meli.desafiospring.model.UserDao;
import lombok.Getter;

@Getter
public class FilterUserRepositoryUtil {

    private UserDao productDAO;
    private Object filter;

    public FilterUserRepositoryUtil(UserDao productDAO, Object filter) {
        this.productDAO = productDAO;
        this.filter = filter;
    }

}
