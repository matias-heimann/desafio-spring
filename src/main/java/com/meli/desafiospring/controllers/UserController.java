package com.meli.desafiospring.controllers;

import com.meli.desafiospring.exceptions.FilterNotValidException;
import com.meli.desafiospring.exceptions.NotValidInformationForNewUserException;
import com.meli.desafiospring.model.NewUser;
import com.meli.desafiospring.model.dto.UserDTO;
import com.meli.desafiospring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    /**
     * Creates user.
     * @param user
     * @return
     * @throws IOException
     * @throws NotValidInformationForNewUserException
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public UserDTO addUser(@RequestBody NewUser user) throws IOException, NotValidInformationForNewUserException {
        return this.userService.addUser(user);
    }

    /**
     * Get users with custom filters.
     * Filters: by id, name, email, country, province and city.
     * @param filters
     * @return
     * @throws FilterNotValidException
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public List<UserDTO> getUsers(@RequestParam HashMap<String, String> filters) throws FilterNotValidException {
        return this.userService.getUsers(filters);
    }
}
