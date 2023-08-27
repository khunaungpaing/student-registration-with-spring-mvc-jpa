package com.khun.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khun.model.dao.CourseRepository;
import com.khun.model.dto.CourseDto;
import com.khun.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

	private CourseRepository courseRepo;

	@Autowired
	public CourseServiceImpl(CourseRepository courseRepo) {
		this.courseRepo = courseRepo;
	}

	@Override
	public boolean save(CourseDto course) throws SQLException {
		return courseRepo.save(course);
	}

	@Override
	public List<CourseDto> fetchAll() {
		return courseRepo.fetchAll();
	}

}
