package com.khun.model.dao;

import java.sql.SQLException;

import com.khun.model.dto.CourseDetailsDto;

public interface CourseDetailsRepository {

	public boolean save(CourseDetailsDto details) throws SQLException;
	public boolean delete(String studentId) throws SQLException;
}
