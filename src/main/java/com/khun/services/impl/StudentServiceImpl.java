package com.khun.services.impl;

import com.khun.dto.StudentDto;
import com.khun.dto.UserDto;
import com.khun.entity.Course;
import com.khun.entity.Student;
import com.khun.entity.User;
import com.khun.repo.CourseRepository;
import com.khun.repo.StudentRepository;
import com.khun.services.StudentService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public boolean addStudentWithEnrollments(StudentDto studentDto, List<String> courseIds) {
        Student student = modelMapper.map(studentDto, Student.class);
        List<Course> courses = courseRepository.findAllById(courseIds);

        Set<Course> courseSet = new HashSet<>(courses);
        student.setCourses(courseSet);

        studentRepository.save(student);

        return true;
    }


    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> fetchAllStudents() {
        List<Student> students = studentRepository.findAll();

        return students.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private StudentDto convertToDto(Student student) {
        StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setDob(student.getDob());
        dto.setGender(student.getGender());
        dto.setPhone(student.getPhone());
        dto.setEducation(student.getEducation());
        dto.setImg_url(student.getImg_url());
        dto.setEnrollment(new ArrayList<>(student.getCourses()));
        dto.setCreatedBy(student.getCreatedBy());
        return dto;
    }


    @Override
    @Transactional
    public boolean update(StudentDto studentDto, List<String> courseIds) {
        Optional<Student> optionalStudent = studentRepository.findById(studentDto.getId());

        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            // Update student's properties with values from studentDto
            student.setName(studentDto.getName());
            student.setDob(studentDto.getDob());
            student.setGender(studentDto.getGender());
            student.setPhone(studentDto.getPhone());
            student.setEducation(studentDto.getEducation());
            student.setImg_url(studentDto.getImg_url());

            // Clear the existing courses
            student.getCourses().clear();

            // Update the courses
            List<Course> updatedCourses = courseRepository.findAllById(courseIds);
            student.setCourses(new HashSet<>(updatedCourses));

            studentRepository.save(student); // Save the updated student entity
            return true;
        }

        return false;
    }


    @Override
    public Long next() {
        return studentRepository.count() + 1;
    }
}
