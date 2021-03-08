package com.meli.desafiospring.repositories.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.desafiospring.model.UserDao;
import com.meli.desafiospring.repositories.UserRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private HashMap<Integer, UserDao> users;
    private String filename;

    public UserRepositoryImpl() throws IOException {
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

}
