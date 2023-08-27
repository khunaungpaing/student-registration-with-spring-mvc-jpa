package com.khun.service;

import java.sql.SQLException;
import java.util.List;

import com.khun.model.dto.CourseDetailsDto;
import com.khun.model.dto.StudentDto;
import com.khun.model.dto.StudentInfoDto;

public interface StudentService {
	boolean save(StudentDto student) throws SQLException;
	List<StudentInfoDto> fetchAll();
	List<StudentDto> fetchAllStudents();
	boolean update(StudentDto user) throws SQLException;
	boolean delete(String id) throws SQLException;
	boolean saveDetails(CourseDetailsDto details) throws SQLException;
	boolean deleteCourseFromJointTable(String studentId) throws SQLException;
	List<StudentInfoDto> findById(String id);
}
