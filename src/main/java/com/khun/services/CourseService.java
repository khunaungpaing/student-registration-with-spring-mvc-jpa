package com.khun.services;

import com.khun.dto.CourseDto;
import com.khun.dto.UserReqDto;
import com.khun.entity.Course;
import com.khun.utils.Predictable;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CourseService extends Predictable {
    public boolean save(CourseDto course) throws SQLException;

    public List<CourseDto> getAllCourses();

    CourseDto convertToDto(Course course);

    void toggleActive(String courseId, boolean active);

    Optional<Course> getOneById(String courseId);

    void update(CourseDto courseDto);
}
