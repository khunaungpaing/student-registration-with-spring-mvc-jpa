package com.khun.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.google.protobuf.Option;
import com.khun.model.dto.StudentDto;
import com.khun.model.dto.StudentInfoDto;

public interface StudentRepository extends Predictable{
	boolean save(StudentDto student) throws SQLException;
	List<StudentInfoDto> fetchAll();
	List<StudentDto> fetchAllStudents();
	boolean update(StudentDto user) throws SQLException;
	List<StudentInfoDto> findById(String id);
	boolean delete(String id) throws SQLException;
	
}
