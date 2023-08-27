package com.khun.model.dao;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

import com.khun.model.dto.CourseDto;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

public interface CourseRepository extends Predictable{
	boolean save(CourseDto course) throws SQLException;
	List<CourseDto> fetchAll();
	
}
