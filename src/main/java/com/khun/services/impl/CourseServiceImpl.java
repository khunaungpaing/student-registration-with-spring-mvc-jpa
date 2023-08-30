package com.khun.services.impl;

import com.khun.dto.CourseDto;
import com.khun.entity.Course;
import com.khun.repo.CourseRepository;
import com.khun.services.CourseService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Long next() {
        return courseRepository.count() + 1;
    }

    @Override
    public boolean save(CourseDto course) throws SQLException {
        Course c = modelMapper.map(course, Course.class);
        courseRepository.save(c);
        return true;
    }

    @Override
    public List<CourseDto> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        Type listType = new TypeToken<List<CourseDto>>() {
        }.getType();
        return modelMapper.map(courses, listType);
    }
}
