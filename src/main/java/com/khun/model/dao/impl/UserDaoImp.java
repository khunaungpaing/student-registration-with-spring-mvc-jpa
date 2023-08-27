package com.khun.model.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.khun.entity.User;
import com.khun.model.dao.UserRepository;
import com.khun.model.dto.UserDto;
import com.khun.model.dto.UserReqDto;
import com.khun.utils.CodeGenerator;
import com.khun.utils.DatabaseUtils;
import com.khun.utils.PasswordHasher;
import com.khun.utils.QueryHelper;
import com.khun.utils.Role;
import com.khun.utils.Status;
import com.khun.utils.Type;

@Repository
public class UserDaoImp implements UserRepository {

	@Override
	public boolean save(UserReqDto user) throws SQLException {
		var success = 0;

		// generate code id
		String code = new CodeGenerator(this).generate(Type.USER);
		System.out.println(code);

		try (var connection = DatabaseUtils.getConnection();
				var preparedStatement = connection.prepareStatement(QueryHelper.INSERT_USER)) {

			preparedStatement.setString(1, code);
			preparedStatement.setString(2, user.getName().trim());
			preparedStatement.setString(3, user.getEmail().trim());
			preparedStatement.setString(4, PasswordHasher.hashPassword(user.getPassword().trim()));
			preparedStatement.setInt(5, Role.USER.getValue());
			preparedStatement.setInt(6, Status.ACTIVE.getValue());

			success = preparedStatement.executeUpdate();
			System.out.println("in UserDaoImpl(): after saving ->"+ success);
		}
		return success > 0;
	}

	@Override
	public boolean update(UserReqDto user, boolean isOldPass) throws SQLException {
		var success = 0;
		
		//check Password
		var pass = isOldPass?user.getPassword():
				PasswordHasher.hashPassword(user.getPassword().trim());
		
		try (Connection connection = DatabaseUtils.getConnection();
				var preparedStatement = connection.prepareStatement(QueryHelper.UPDATE_USER)) {

			preparedStatement.setString(1, user.getName().trim());
			preparedStatement.setString(2, user.getEmail().trim());
			preparedStatement.setString(3, pass);
			preparedStatement.setInt(4, Role.USER.getValue());
			preparedStatement.setInt(5, user.getStatus());
			preparedStatement.setString(6, user.getId());
			success = preparedStatement.executeUpdate();
		}
		return success > 0;
	}

	@Override
	public User findById(String id) {

        User user = new User();
	    try (Connection connection = DatabaseUtils.getConnection();
	         var preparedStatement = connection.prepareStatement(QueryHelper.FIND_BY_USER_ID)) {

	        preparedStatement.setString(1, id);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            user.setId(resultSet.getString("user_id"));
	            user.setName(resultSet.getString("name"));
	            user.setEmail(resultSet.getString("email"));
	            user.setPassword(resultSet.getString("password"));
	            user.setRole(resultSet.getInt("role"));
	            user.setStatus(resultSet.getInt("status"));
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return user;
	}


	@Override
	public int next() {
		int userCount = 0;

		try (Connection connection = DatabaseUtils.getConnection();
				var preparedStatement = connection.prepareStatement(QueryHelper.FETCH_USER_COUNT)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				userCount = resultSet.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return userCount;
	}

	@Override
	public User findByEmail(String email) {
		User user =  null;

		try (Connection connection = DatabaseUtils.getConnection();
				var preparedStatement = connection.prepareStatement(QueryHelper.FIND_BY_USER_EMAIL)) {

			preparedStatement.setString(1, email);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				user = new User();
				user.setId(resultSet.getString("user_id"));
				user.setEmail(resultSet.getString("email"));
				user.setName(resultSet.getString("name"));
				user.setPassword(resultSet.getString("password"));
				user.setStatus(resultSet.getInt("status"));
				user.setRole(resultSet.getInt("role"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			// Handle the exception as needed
		}

		return user;
	}

	@Override
	public List<UserDto> fetchAll() {
		List<UserDto> usersList = new ArrayList<UserDto>();
		try (Connection connection = DatabaseUtils.getConnection();
				var preparedStatement = connection.prepareStatement(QueryHelper.FETCH_ALL_USER)) {

			preparedStatement.setInt(1, Role.USER.getValue());
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				UserDto user = new UserDto();
				user.setId(resultSet.getString("user_id"));
				user.setEmail(resultSet.getString("email"));
				user.setName(resultSet.getString("name"));
				user.setStatus(resultSet.getInt("status"));
				user.setRole(resultSet.getInt("role"));

				usersList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return usersList;
	}

	@Override
	public boolean disableOrActiveUser(String userId, int status) throws SQLException {
		var success = 0;
		
		
		try (Connection connection = DatabaseUtils.getConnection();
				var preparedStatement = connection.prepareStatement(QueryHelper.DISABLE_OR_ENABLE_USER)) {

			preparedStatement.setInt(1, status);
			preparedStatement.setString(2, userId);
			System.out.println("diasble or enable ....");
			success = preparedStatement.executeUpdate();
			System.out.println("success -> "+success);
			System.out.println("------------------------------\n");
		}
		return success > 0;
	}

}
