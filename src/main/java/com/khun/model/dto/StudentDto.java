package com.khun.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentDto {
	private String id;
	private String name;
	private Date dob;
	private int gender;
	private String phone;
	private String education;
	private String img_url;
	
}