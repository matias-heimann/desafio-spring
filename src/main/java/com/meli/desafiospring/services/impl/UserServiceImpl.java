package com.meli.desafiospring.services.impl;

import com.meli.desafiospring.exceptions.FilterNotValidException;
import com.meli.desafiospring.exceptions.NotValidInformationForNewUserException;
import com.meli.desafiospring.model.NewUser;
import com.meli.desafiospring.model.UserDao;
import com.meli.desafiospring.model.dto.UserDTO;
import com.meli.desafiospring.repositories.UserRepository;
import com.meli.desafiospring.services.UserService;
import com.meli.desafiospring.utils.EmailValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO addUser(NewUser user) throws IOException, NotValidInformationForNewUserException {
        List<UserDao> users = this.userRepository.getAll();
        if(user.getName() == null || user.getName().equals("")
                || user.getEmail() == null || user.getEmail().equals("")
                || user.getCountry() == null || user.getCountry().equals("")
                || user.getProvince() == null || user.getProvince().equals("")
                || user.getCity() == null || user.getCity().equals("")){
            throw new NotValidInformationForNewUserException("Some mandatory fields are empty");
        }

        if(!EmailValidatorUtil.isValidEmailAddress(user.getEmail())){
            throw new NotValidInformationForNewUserException("The email is not valid");
        }

        if(this.userRepository.filterByEmail(user.getEmail()).size() > 0){
            throw new NotValidInformationForNewUserException("Email already in use");
        }

        UserDao userDao = this.userRepository.save(new UserDao(user.getName(), user.getEmail(), user.getCountry(), user.getProvince(), user.getCity()));
        return new UserDTO(userDao.getId(), userDao.getName(), userDao.getEmail(), userDao.getCountry(), userDao.getProvince(), userDao.getCity());
    }

    @Override
    public List<UserDTO> getUsers(HashMap<String, String> filters) throws FilterNotValidException {
        for(Map.Entry<String, String> entry: filters.entrySet()){
            filters.put(entry.getKey(), entry.getValue().toLowerCase(Locale.ROOT));
        }
        List<UserDao> userDaos = this.userRepository.getUsers(filters);
        return userDaos.stream().map(u -> new UserDTO(u.getId(), u.getName(), u.getEmail(), u.getCountry(), u.getProvince(), u.getCity()))
                .collect(Collectors.toList());
    }
}
