package com.khun.model.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.khun.model.dao.CourseRepository;
import com.khun.model.dto.CourseDto;
import com.khun.utils.CodeGenerator;
import com.khun.utils.DatabaseUtils;
import com.khun.utils.PasswordHasher;
import com.khun.utils.QueryHelper;
import com.khun.utils.Role;
import com.khun.utils.Status;
import com.khun.utils.Type;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

@Repository
public class CourseDaoImpl implements CourseRepository {

	@Override
	public int next() {
		int courseCount = 0;

		try (Connection connection = DatabaseUtils.getConnection();
				var preparedStatement = connection.prepareStatement(QueryHelper.FETCH_COURSE_COUNT)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				courseCount = resultSet.getInt(1)+1;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return courseCount;
	}

	@Override
	public boolean save(CourseDto course) throws SQLException {
		var success = 0;

		// generate code id
		String code = new CodeGenerator(this).generate(Type.COURSE);
		System.out.println(code);

		try (Connection connection = DatabaseUtils.getConnection();
				var preparedStatement = connection.prepareStatement(QueryHelper.INSERT_COURSE)) {

			preparedStatement.setString(1, code);
			preparedStatement.setString(2, course.getName().trim());
			success = preparedStatement.executeUpdate();
		}
		return success > 0;
	}


	@Override
	public List<CourseDto> fetchAll() {
		List<CourseDto> courseList = new ArrayList<CourseDto>();
		try (Connection connection = DatabaseUtils.getConnection();
				var preparedStatement = connection.prepareStatement(QueryHelper.FETCH_ALL_COURSE)) {

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				CourseDto course = new CourseDto();
				course.setId(resultSet.getString("course_id"));
				course.setName(resultSet.getString("course_name"));

				courseList.add(course);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return courseList;
	}

}
