package com.khun.model.dao;


import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.khun.entity.User;
import com.khun.model.dto.UserDto;
import com.khun.model.dto.UserReqDto;

public interface UserRepository extends Predictable{
	public boolean save(UserReqDto user) throws SQLException;
	public User findById(String id);
	public User findByEmail(String email);
	public List<UserDto> fetchAll();
	public boolean disableOrActiveUser(String userId, int status) throws SQLException;
	boolean update(UserReqDto user, boolean isOldPass) throws SQLException;
}
