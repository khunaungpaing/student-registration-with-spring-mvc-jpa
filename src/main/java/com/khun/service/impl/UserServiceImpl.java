package com.khun.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khun.entity.User;
import com.khun.model.dao.UserRepository;
import com.khun.model.dto.UserDto;
import com.khun.model.dto.UserReqDto;
import com.khun.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private UserRepository userRepo;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public boolean save(UserReqDto user) throws SQLException {
		return userRepo.save(user);
	}

	@Override
	public User findById(String id) {
		return userRepo.findById(id);
	}

	@Override
	public User findByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public List<UserDto> fetchAll() {
		return userRepo.fetchAll();
	}

	@Override
	public boolean disableOrActiveUser(String userId, int status) throws SQLException {
		return userRepo.disableOrActiveUser(userId, status);
	}

	@Override
	public boolean update(UserReqDto user, boolean isOldPass) throws SQLException {
		return userRepo.update(user, isOldPass);
	}

}
