package com.meli.desafiospring.repositories.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.desafiospring.exceptions.FilterNotValidException;
import com.meli.desafiospring.model.UserDao;
import com.meli.desafiospring.repositories.UserRepository;
import com.meli.desafiospring.utils.FilterUserRepositoryUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private HashMap<Integer, UserDao> users;
    private HashMap<String, Function<FilterUserRepositoryUtil, Boolean>> filters;

    @Value("${users-json}")
    private String filename;

    public UserRepositoryImpl() throws IOException {
        System.out.println(filename);
        this.addFilters();
        this.filename = "src/main/resources/static/users.json";
        this.users = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        List<UserDao> userList = objectMapper.readValue(new File(this.filename),
                new TypeReference<>() {
        });
        userList.forEach(u -> this.users.put(u.getId(), u));
    }

    @Override
    public UserDao save(UserDao userDao) {
        if(userDao.getId() == null){
            userDao.setId(this.users.size());
        }
        this.users.put(userDao.getId(), userDao);
        this.writeInFile();
        return userDao;
    }

    @Override
    public List<UserDao> getAll() {
        return this.users.values().stream().collect(Collectors.toList());
    }

    @Override
    public List<UserDao> filterByEmail(String email) {
        return this.users.values().stream().filter(u -> u.getEmail().equals(email)).collect(Collectors.toList());
    }

    @Override
    public List<UserDao> getUsers(HashMap<String, String> filters) throws FilterNotValidException {
        List<UserDao> userDaos = this.users.values().stream().collect(Collectors.toList());
        for(Map.Entry<String, String> entry: filters.entrySet()){
            if(this.filters.get(entry.getKey()) == null){
                throw new FilterNotValidException("Filter with name " + entry.getKey() + " is not valid");
            }
            try {
                userDaos = userDaos.stream().filter(u -> this.filters.get(entry.getKey())
                        .apply(new FilterUserRepositoryUtil(u, entry.getValue())))
                        .collect(Collectors.toList());
            }
            catch (NumberFormatException numberFormatException){
                throw new FilterNotValidException(entry.getValue() + " is not a valid format for filter " + entry.getKey());
            }
        }
        return userDaos;
    }

    private void writeInFile() {
        JSONArray jsonArray = new JSONArray();
        this.users.values().stream().map(u -> this.jsonifyObject(u)).forEach(i -> jsonArray.add(i));
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(this.filename);
            fileWriter.write(jsonArray.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private JSONObject jsonifyObject(UserDao userDao){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", userDao.getId());
        jsonObject.put("name", userDao.getName());
        jsonObject.put("email", userDao.getEmail());
        jsonObject.put("country", userDao.getCountry());
        jsonObject.put("province", userDao.getProvince());
        jsonObject.put("city", userDao.getCity());
        return jsonObject;
    }


    private void addFilters(){
        this.filters = new HashMap<>();
        this.filters.put("id", (FilterUserRepositoryUtil filter) ->
                filter.getProductDAO().getId().equals(Integer.valueOf(filter.getFilter().toString())));
        this.filters.put("name", (FilterUserRepositoryUtil filter) ->
                filter.getProductDAO()
                        .getName()
                        .toLowerCase(Locale.ROOT)
                        .equals((String) filter.getFilter()));
        this.filters.put("email", (FilterUserRepositoryUtil filter) ->
                filter.getProductDAO()
                        .getEmail()
                        .toLowerCase(Locale.ROOT)
                        .equals((String) filter.getFilter()));
        this.filters.put("country", (FilterUserRepositoryUtil filter) ->
                filter.getProductDAO()
                        .getCountry()
                        .toLowerCase(Locale.ROOT)
                        .equals((String) filter.getFilter()));
        this.filters.put("province", (FilterUserRepositoryUtil filter) ->
                filter.getProductDAO()
                        .getProvince()
                        .toLowerCase(Locale.ROOT)
                        .equals((String) filter.getFilter()));
        this.filters.put("city", (FilterUserRepositoryUtil filter) ->
                filter.getProductDAO()
                        .getCity()
                        .toLowerCase(Locale.ROOT)
                        .equals((String) filter.getFilter()));

    }
}
