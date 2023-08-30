package com.khun.dto;

import java.io.Serializable;

import com.mysql.cj.log.Log;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class UserReqDto implements Serializable{
	private static final long serialVersionUID = 3600915012344180439L;
	private String id;
	private String name;
	private String email;
	private String password;
	private int role;
	private int status;
}
