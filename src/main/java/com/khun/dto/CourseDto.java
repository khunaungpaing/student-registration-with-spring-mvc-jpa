package com.khun.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseDto implements Serializable{

	private static final long serialVersionUID = 8915504654857823697L;
	
	private String id;
	private String name;

}
