package com.meli.desafiospring.controllers;

import com.meli.desafiospring.exceptions.NotValidInformationForNewUserException;
import com.meli.desafiospring.model.NewUser;
import com.meli.desafiospring.model.dto.UserDTO;
import com.meli.desafiospring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public UserDTO addUser(@RequestBody NewUser user) throws IOException, NotValidInformationForNewUserException {
        return this.userService.addUser(user);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public List<UserDTO> getUsers(){
        return null;
    }
}
