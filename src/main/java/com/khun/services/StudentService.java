package com.khun.services;

import com.khun.dto.StudentDto;
import com.khun.utils.Predictable;

import java.sql.SQLException;
import java.util.List;

public interface StudentService extends Predictable {
    public boolean addStudentWithEnrollments(StudentDto studentDto, List<String> courseIds) throws SQLException;

    List<StudentDto> fetchAllStudents();

    boolean update(StudentDto studentDto, List<String> courseIds) throws SQLException;

}
