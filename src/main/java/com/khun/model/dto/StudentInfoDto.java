package com.khun.model.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class StudentInfoDto {
	private String student_id;
	private String name;
	private Date dob;
	private int gender;
	private String phone;
	private String education;
	private String img_url;
	private String course_id;
	private String course_name;
	
	
	public CourseDto mapToCourseDto() {
		return new CourseDto(course_id, course_name);
	}
	public StudentDto mapToStudentDto() {
		return new StudentDto(student_id,name,dob,gender,phone,education,img_url);
	}
}
