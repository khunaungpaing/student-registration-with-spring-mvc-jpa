package com.khun.entity;


import com.khun.dto.UserDto;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private int role;
    private int status;
    private boolean firstLogin;
    private String imgUrl;

    public UserDto mapToUserDto() {
        return new UserDto(id, name, email, role, status, firstLogin, imgUrl);
    }
}
