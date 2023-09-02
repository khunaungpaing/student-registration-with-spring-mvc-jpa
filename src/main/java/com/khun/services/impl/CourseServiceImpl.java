package com.khun.services.impl;

import com.khun.dto.CourseDto;
import com.khun.dto.StudentDto;
import com.khun.entity.Course;
import com.khun.entity.Student;
import com.khun.entity.User;
import com.khun.repo.CourseRepository;
import com.khun.services.CourseService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Transactional(readOnly = true)
    public List<CourseDto> getAllCourses() {
        List<Course> courses = courseRepository.findAll();

        return courses.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CourseDto convertToDto(Course course) {
        CourseDto courseDto = new CourseDto();
        courseDto.setId(course.getId());
        courseDto.setName(course.getName());
        courseDto.setCreatedAt(course.getCreatedAt());
        courseDto.setActive(course.isActive());
        courseDto.setStudents(new ArrayList<>(course.getStudents()));

        return courseDto;
    }

    @Override
    public void toggleActive(String courseId, boolean active) {
        courseRepository.toggleActive(courseId, active);
    }

    @Override
    public Optional<Course> getOneById(String courseId) {
        return courseRepository.findById(courseId);
    }

    @Override
    public void update(CourseDto courseDto) {
        Optional<Course> optionalUser = courseRepository.findById(courseDto.getId());
        if (optionalUser.isPresent()) {
            Course existingUser = optionalUser.get();
            modelMapper.map(courseDto, existingUser);
            courseRepository.save(existingUser);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + courseDto.getId());
        }
    }
}
