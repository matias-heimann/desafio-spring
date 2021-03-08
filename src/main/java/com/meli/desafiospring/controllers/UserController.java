package com.meli.desafiospring.controllers;

import com.meli.desafiospring.exceptions.FilterNotExistException;
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
import java.util.Locale;

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
    public List<UserDTO> getUsers(@RequestParam(required = false, defaultValue = "") Integer id,
                                  @RequestParam(required = false, defaultValue = "") String name,
                                  @RequestParam(required = false, defaultValue = "") String email,
                                  @RequestParam(required = false, defaultValue = "") String country,
                                  @RequestParam(required = false, defaultValue = "") String province,
                                  @RequestParam(required = false, defaultValue = "") String city) throws FilterNotExistException {
        HashMap<String, Object> filter = new HashMap<>();
        if(id != null){
            filter.put("id", id);
        }
        if(!name.equals("")){
            filter.put("name", name.toLowerCase(Locale.ROOT));
        }
        if(!email.equals("")){
            filter.put("email", email.toLowerCase(Locale.ROOT));
        }
        if(!country.equals("")){
            filter.put("country", country.toLowerCase(Locale.ROOT));
        }
        if(!province.equals("")){
            filter.put("province", province.toLowerCase(Locale.ROOT));
        }
        if(!city.equals("")){
            filter.put("city", city.toLowerCase(Locale.ROOT));
        }
        return this.userService.getUsers(filter);
    }
}
