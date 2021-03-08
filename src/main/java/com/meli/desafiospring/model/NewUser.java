package com.meli.desafiospring.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NewUser {

    private String name;
    private String email;
    private String country;
    private String province;
    private String city;

    public NewUser(){
    }

    public NewUser(String name, String email, String country, String province, String city) {
        this.name = name;
        this.email = email;
        this.country = country;
        this.province = province;
        this.city = city;
    }

    @Override
    public String toString() {
        return "NewUser{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
