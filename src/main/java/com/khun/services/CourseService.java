package com.khun.services;

import com.khun.dto.CourseDto;
import com.khun.utils.Predictable;

import java.sql.SQLException;
import java.util.List;

public interface CourseService extends Predictable {
    public boolean save(CourseDto course) throws SQLException;

    public List<CourseDto> getAllCourses();
}
