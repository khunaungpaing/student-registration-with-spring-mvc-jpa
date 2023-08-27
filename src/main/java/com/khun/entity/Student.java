package com.khun.entity;

import java.sql.Date;

import lombok.Data;

@Data
public class Student {
	private String id;
	private String name;
	private Date dob;
	private int gender;
	private String phone;
	private String education;
	private String img_url;
	
}
