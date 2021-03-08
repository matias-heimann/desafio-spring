package com.meli.desafiospring.services;

import com.meli.desafiospring.exceptions.FilterNotExistException;
import com.meli.desafiospring.exceptions.NotValidInformationForNewUserException;
import com.meli.desafiospring.model.NewUser;
import com.meli.desafiospring.model.dto.UserDTO;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface UserService {

    public UserDTO addUser(NewUser user) throws IOException, NotValidInformationForNewUserException;
    public List<UserDTO> getUsers(HashMap<String, Object> filters) throws FilterNotExistException;

}
