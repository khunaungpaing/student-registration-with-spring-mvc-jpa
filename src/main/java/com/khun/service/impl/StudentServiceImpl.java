package com.khun.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khun.model.dao.CourseDetailsRepository;
import com.khun.model.dao.StudentRepository;
import com.khun.model.dto.CourseDetailsDto;
import com.khun.model.dto.StudentDto;
import com.khun.model.dto.StudentInfoDto;
import com.khun.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService{
	
	private StudentRepository studentRepository;
	private CourseDetailsRepository detailsRepo;
	
	@Autowired
	public StudentServiceImpl(StudentRepository studentRepository,CourseDetailsRepository detailsRepo) {
		this.studentRepository = studentRepository;
		this.detailsRepo = detailsRepo;
	}

	@Override
	public boolean save(StudentDto student) throws SQLException {
		return studentRepository.save(student);
	}

	@Override
	public List<StudentInfoDto> fetchAll(){
		return studentRepository.fetchAll();
	}

	@Override
	public List<StudentDto> fetchAllStudents() {
		return studentRepository.fetchAllStudents();
	}

	@Override
	public boolean update(StudentDto user) throws SQLException {
		return studentRepository.update(user);
	}

	@Override
	public boolean delete(String id) throws SQLException {
		return studentRepository.delete(id);
	}

	@Override
	public boolean saveDetails(CourseDetailsDto details) throws SQLException {
		return detailsRepo.save(details);
	}

	@Override
	public boolean deleteCourseFromJointTable(String studentId) throws SQLException {
		return detailsRepo.delete(studentId);
	}

	@Override
	public List<StudentInfoDto> findById(String id) {
		return studentRepository.findById(id);
	}

}
