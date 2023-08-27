package com.khun.model.dao.impl;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.khun.model.dao.CourseDetailsRepository;
import com.khun.model.dto.CourseDetailsDto;
import com.khun.utils.DatabaseUtils;
import com.khun.utils.QueryHelper;

@Repository
public class DetailsDaoImpl implements CourseDetailsRepository {

	@Override
	public boolean save(CourseDetailsDto details) {
		int rowsAffected = 0;
		try (var connection = DatabaseUtils.getConnection();
				var preparedStatement = connection.prepareStatement(QueryHelper.INSERT_DETAILS)) {

			preparedStatement.setString(1, details.getStudent_id());
			preparedStatement.setString(2, details.getCourse_id());
			rowsAffected = preparedStatement.executeUpdate();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return rowsAffected > 0;
	}

	@Override
	public boolean delete(String studentId) {
		int rowsAffected = 0;
		try (var connection = DatabaseUtils.getConnection();
	             var preparedStatement = connection.prepareStatement(QueryHelper.DELETE_DETAILS)) {

	            preparedStatement.setString(1, studentId);
	            
	            rowsAffected = preparedStatement.executeUpdate();
	            
	        }catch (SQLException e) {
				e.printStackTrace();
			}
		return rowsAffected > 0;
    }

}
