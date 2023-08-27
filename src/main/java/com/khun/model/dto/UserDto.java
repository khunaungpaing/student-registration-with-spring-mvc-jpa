package com.khun.model.dto;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto implements Serializable{
	
	private static final long serialVersionUID = 8723481445030363622L;
	
	private String id;
	private String name;
	private String email;
	private int role;
	private int status;
}
