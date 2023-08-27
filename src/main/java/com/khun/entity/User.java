package com.khun.entity;


import com.khun.model.dto.UserDto;

import lombok.Data;

@Data
public class User {
	private String id;
	private String name;
	private String email;
	private String password;
	private int role;
	private int status;
	
	public UserDto mapToUserDto() {
		return new UserDto(id,name,email,role,status);
	}
}
