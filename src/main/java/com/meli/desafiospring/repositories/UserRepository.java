package com.meli.desafiospring.repositories;

import com.meli.desafiospring.exceptions.FilterNotValidException;
import com.meli.desafiospring.model.UserDao;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface UserRepository {

    public UserDao save(UserDao userDao) throws IOException;
    public List<UserDao> getAll();
    public List<UserDao> filterByEmail(String email);
    public List<UserDao> getUsers(HashMap<String, String> filters) throws FilterNotValidException;
}
