package com.khun.service;

import java.sql.SQLException;
import java.util.List;

import com.khun.model.dto.CourseDto;

public interface CourseService {
	public boolean save(CourseDto course) throws SQLException;
	public List<CourseDto> fetchAll();
}
