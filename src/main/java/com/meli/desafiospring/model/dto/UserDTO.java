package com.meli.desafiospring.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDTO {

    private Integer id;
    private String name;
    private String email;
    private String country;
    private String province;
    private String city;

    public UserDTO(Integer id, String name, String email, String country, String province, String city) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.country = country;
        this.province = province;
        this.city = city;
    }
}
