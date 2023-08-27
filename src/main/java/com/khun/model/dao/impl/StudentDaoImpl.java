package com.khun.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.khun.model.dao.StudentRepository;
import com.khun.model.dto.StudentDto;
import com.khun.model.dto.StudentInfoDto;
import com.khun.utils.CodeGenerator;
import com.khun.utils.DatabaseUtils;
import com.khun.utils.PasswordHasher;
import com.khun.utils.QueryHelper;
import com.khun.utils.Role;
import com.khun.utils.Type;

@Repository
public class StudentDaoImpl implements StudentRepository {

	@Override
	public int next() {
		int studentCount = 0;

		try (Connection connection = DatabaseUtils.getConnection();
				var preparedStatement = connection.prepareStatement(QueryHelper.FETCH_STUDENT_COUNT)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				studentCount = resultSet.getInt(1)+1;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return studentCount;
	}

	@Override
	public boolean save(StudentDto student) throws SQLException {
		var success = 0;

		// generate code id
		String code = new CodeGenerator(this).generate(Type.STUDENT);

		try (Connection connection = DatabaseUtils.getConnection();
				var preparedStatement = connection.prepareStatement(QueryHelper.INSERT_STUDENT)) {

			preparedStatement.setString(1, code);
			preparedStatement.setString(2, student.getName().trim());
			preparedStatement.setDate(3, student.getDob());
			preparedStatement.setInt(4, student.getGender());
			preparedStatement.setString(5, student.getPhone());
			preparedStatement.setString(6, student.getEducation().trim());
			preparedStatement.setString(7, student.getImg_url());

			success = preparedStatement.executeUpdate();
		}
		return success > 0;
	}

	@Override
	public List<StudentInfoDto> fetchAll() {

		List<StudentInfoDto> studentList = new ArrayList<StudentInfoDto>();

		try (Connection connection = DatabaseUtils.getConnection();
				var preparedStatement = connection.prepareStatement(QueryHelper.FETCH_ALL_STUDENT_INFO)) {

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				StudentInfoDto user = new StudentInfoDto();
				user.setStudent_id(resultSet.getString(1));
				user.setName(resultSet.getString(2));
				user.setDob(resultSet.getDate(3));
				user.setGender(resultSet.getInt(4));
				user.setPhone(resultSet.getString(5));
				user.setEducation(resultSet.getString(6));
				user.setImg_url(resultSet.getString(7));
				user.setCourse_id(resultSet.getString(8));
				user.setCourse_name(resultSet.getString(9));
				studentList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return studentList;
	}

	@Override
	public boolean update(StudentDto user) throws SQLException {
		var success = 0;
		
		
		try (var connection = DatabaseUtils.getConnection();
				var preparedStatement = connection.prepareStatement(QueryHelper.UPDATE_STUDENT)) {
			preparedStatement.setString(1, user.getName().trim());
			preparedStatement.setDate(2, user.getDob());
			preparedStatement.setInt(3, user.getGender());
			preparedStatement.setString(4, user.getPhone());
			preparedStatement.setString(5, user.getEducation());
			preparedStatement.setString(6, user.getImg_url());
			preparedStatement.setString(7, user.getId());
			success = preparedStatement.executeUpdate();
			System.out.println("\n\nsuccess -> "+success);
		}
		return success > 0;
	}

	@Override
	public List<StudentDto> fetchAllStudents() {
		List<StudentDto> studentList = new ArrayList<StudentDto>();

		try (Connection connection = DatabaseUtils.getConnection();
				var preparedStatement = connection.prepareStatement(QueryHelper.FETCH_ALL_STUDENT)) {

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				StudentDto user = new StudentDto();
				user.setId(resultSet.getString(1));
				user.setName(resultSet.getString(2));
				user.setDob(resultSet.getDate(3));
				user.setGender(resultSet.getInt(4));
				user.setPhone(resultSet.getString(5));
				user.setEducation(resultSet.getString(6));
				user.setImg_url(resultSet.getString(7));
				studentList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return studentList;
	}

	@Override
	public boolean delete(String id) {
		int rowsAffected = 0;
		try (var connection = DatabaseUtils.getConnection();
	             var preparedStatement = connection.prepareStatement(QueryHelper.DELETE_STUDENT)) {

	            preparedStatement.setString(1, id);
	            
	            rowsAffected = preparedStatement.executeUpdate();
	            
	        }catch (SQLException e) {
				e.printStackTrace();
			}
		return rowsAffected > 0;
	}

	@Override
	public List<StudentInfoDto> findById(String id) {
		List<StudentInfoDto> studentList = new ArrayList<StudentInfoDto>();
		

		try (Connection connection = DatabaseUtils.getConnection();
				var preparedStatement = connection.prepareStatement(QueryHelper.FIND_STUDENT_INFO_BY_ID)) {

			preparedStatement.setString(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				StudentInfoDto student = new StudentInfoDto();
				student.setStudent_id(resultSet.getString(1));
				student.setName(resultSet.getString(2));
				student.setDob(resultSet.getDate(3));
				student.setGender(resultSet.getInt(4));
				student.setPhone(resultSet.getString(5));
				student.setEducation(resultSet.getString(6));
				student.setImg_url(resultSet.getString(7));
				student.setCourse_id(resultSet.getString(8));
				student.setCourse_name(resultSet.getString(9));
				studentList.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return studentList;
	}

}
